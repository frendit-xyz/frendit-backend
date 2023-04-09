package frendit.xyz.com.controller;

import frendit.xyz.com.entity.AuthEntity;
import frendit.xyz.com.entity.GoogleToken;
import frendit.xyz.com.model.GoogleSigninModel;
import frendit.xyz.com.model.SigninModel;
import frendit.xyz.com.model.SignupModel;
import frendit.xyz.com.model.TokenModel;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService, ProfileService profileService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String TestAuth(){
        return "Auth OK";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> SignUp(@Valid @RequestBody SignupModel signupModel) throws Exception {
        try{
            authService.createAuth(signupModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
        } catch(Exception e){
            throw new Exception("Account couldn't be created with provided data");
        }
    }

    @PostMapping("/sign-in")
    public TokenModel SignIn(@Valid @RequestBody SigninModel signinModel) throws AuthException {
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
