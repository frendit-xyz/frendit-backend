package frendit.xyz.com.controller;

import frendit.xyz.com.entity.auth.AuthEntity;
import frendit.xyz.com.entity.auth.GoogleToken;
import frendit.xyz.com.model.auth.GoogleSigninModel;
import frendit.xyz.com.model.auth.SigninModel;
import frendit.xyz.com.model.auth.SignupModel;
import frendit.xyz.com.model.auth.TokenModel;
import frendit.xyz.com.model.response.Message;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.SecureTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    private final SecureTokenService secureTokenService;

    public AuthController(AuthService authService, SecureTokenService secureTokenService) {
        this.authService = authService;
        this.secureTokenService = secureTokenService;
    }

    @GetMapping("/test")
    public String TestAuth(){
        return "Auth OK";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Message> SignUp(@Valid @RequestBody SignupModel signupModel) throws Exception {
        try{
            authService.createAuth(signupModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Account created successfully"));
        } catch(Exception e){
            throw new Exception("Account couldn't be created with provided data");
        }
    }

    @PostMapping("/sign-in")
    public TokenModel SignIn(@Valid @RequestBody SigninModel signinModel) throws Exception {
        AuthEntity authEntity = authService.findByUsernameOrEmail(
                signinModel.getUsername(),
                signinModel.getEmail()
        );
        boolean isAuthenticated = authService.verifyPassword(
                signinModel.getPassword(),
                authEntity.getHashed_password()
        );
        if(isAuthenticated){
            return authService.getNewToken(authEntity);
        } else {
            throw new AuthException("Password doesn't match");
        }
    }

    @GetMapping("/verify/{token}")
    public boolean VerifyAccount(@PathVariable String token) {
        String email = authService.getEmailOfLoggedUser();
        return secureTokenService.verifySecureToken(email, token);
    }

    @PostMapping("/refresh-token")
    public TokenModel RefreshToken(@RequestBody TokenModel tokenModel) {
        return authService.reIssueToken(tokenModel);
    }

    @PostMapping("/google")
    public TokenModel GoogleSignIn(@RequestBody GoogleSigninModel googleSigninModel) throws Exception {
        GoogleToken googleIdToken = authService.extractGooleToken(googleSigninModel.getToken());
        if(googleIdToken != null){
            return authService.createTokenFromGoogle(googleIdToken);
        } else {
            throw new AuthException("Invalid Google Account!");
        }
    }
}
