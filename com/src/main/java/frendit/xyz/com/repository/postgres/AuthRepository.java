package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.entity.AuthEntity;
import frendit.xyz.com.entity.GoogleToken;
import frendit.xyz.com.model.IssueTokenModel;
import frendit.xyz.com.model.SignupModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthRepository {
    AuthEntity findByUsernameOrEmail(String username, String email);

    AuthEntity findByEmail(String email);

    void createAuth(SignupModel signupModel);

    void createGoogleAuth(@Param("google") GoogleToken googleToken, @Param("auth") SignupModel signupModel);

    Boolean updateToken(IssueTokenModel issueTokenModel);
}
