package frendit.xyz.com.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import frendit.xyz.com.entity.auth.AuthEntity;
import frendit.xyz.com.entity.auth.GoogleToken;
import frendit.xyz.com.helpers.Converter;
import frendit.xyz.com.model.auth.IssueTokenModel;
import frendit.xyz.com.model.auth.SignupModel;
import frendit.xyz.com.model.auth.TokenModel;
import frendit.xyz.com.repository.postgres.AuthRepository;
import frendit.xyz.com.repository.postgres.ProfileRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Transactional
public class AuthService {
    @Value("${jwt.accesstoken.expirationtime}")
    long accessTokenExpTime;

    @Value("${jwt.refreshtoken.expirationtime}")
    long refreshTokenExpTime;

    @Value("${jwt.accesstoken.secretkey}")
    String accessTokenSecret;

    @Value("${google.client_id}")
    String client_id;

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    private final Converter converter;

    public AuthService(AuthRepository authRepository, ProfileRepository profileRepository, @Lazy PasswordEncoder passwordEncoder, ProfileRepository profileRepository1, Converter converter) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
    }

    public AuthEntity findByUsernameOrEmail(String username, String email){
        return authRepository.findByUsernameOrEmail(username, email);
    }

    public AuthEntity findByEmail(String email){
        return authRepository.findByEmail(email);
    }

    public void createAuth(SignupModel signupModel){
        signupModel.setHashed_password(passwordEncoder.encode(signupModel.getPassword()));
        authRepository.createAuth(signupModel);
    }

    public void createGoogleAuth(GoogleToken googleToken, SignupModel signupModel){
        authRepository.createGoogleAuth(googleToken, signupModel);
    }

    public Boolean verifyPassword(String password, String hashed_password){
        return passwordEncoder.matches(password, hashed_password);
    }

    public String getAccessToken(AuthEntity authEntity){
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(authEntity.getEmail())
                .withClaim("id", authEntity.getId())
                .withClaim("username", authEntity.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(accessTokenExpTime, ChronoUnit.SECONDS)))
                .sign(Algorithm.HMAC256(accessTokenSecret));
    }

    public String getRefreshToken() {
        return passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(25));
    }

    public Timestamp getRefreshedAt() {
        return Timestamp.from(Instant.now());
    }

    public TokenModel getNewToken(AuthEntity authEntity){
        IssueTokenModel issueTokenModel = new IssueTokenModel();
        issueTokenModel.setId(authEntity.getId());
        issueTokenModel.setRefresh_token(getRefreshToken());
        issueTokenModel.setRefreshed_at(getRefreshedAt());
        authRepository.updateToken(issueTokenModel);

        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccess_token(getAccessToken(authEntity));
        tokenModel.setRefresh_token(passwordEncoder.encode(issueTokenModel.getRefresh_token()));
        return tokenModel;
    }

    public boolean verifyRefreshToken(TokenModel tokenModel, AuthEntity authEntity){
        Boolean hasOldToken = (authEntity.getRefreshed_at() != null && StringUtils.isNotEmpty(authEntity.getRefresh_token()));
        Boolean hasValidity = authEntity.getRefreshed_at().plus(refreshTokenExpTime, ChronoUnit.SECONDS).isAfter(Instant.now());
        Boolean isValid = passwordEncoder.matches(authEntity.getRefresh_token(), tokenModel.getRefresh_token());
        return (hasOldToken && hasValidity && isValid);
    }

    public TokenModel reIssueToken(TokenModel tokenModel){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(accessTokenSecret)).build();
        String email = verifier.verify(tokenModel.getAccess_token()).getSubject();
        AuthEntity authEntity = findByEmail(email);

        TokenModel newToken = new TokenModel();
        if(verifyRefreshToken(tokenModel ,authEntity)){
            newToken = getNewToken(authEntity);
        };
        return newToken;
    }

    public GoogleToken extractGooleToken(String token) throws Exception {
        String endpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
        String authorizationHeader = "Bearer " + token;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", authorizationHeader)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        GoogleToken googleToken = new Gson().fromJson(responseBody, GoogleToken.class);
        if(googleToken.isValid()) {
            return googleToken;
        } else {
            throw new Exception("Token expired or not valid. Try again.");
        }
    }

    public TokenModel createTokenFromGoogle(GoogleToken googleToken){
        SignupModel signupModel = new SignupModel();
        signupModel.setEmail(googleToken.getEmail());
        signupModel.setUsername(converter.convertToAlphanumericWithUnderscore((googleToken.getFamily_name() + '.' + googleToken.getGiven_name())));
        createGoogleAuth(googleToken, signupModel);
        AuthEntity authEntity = findByEmail(googleToken.getEmail());
        return getNewToken(authEntity);
    }

    public String getEmailOfLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
