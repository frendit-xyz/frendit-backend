package just.khao.com.repository.postgres;

import just.khao.com.entity.AuthEntity;
import just.khao.com.entity.GoogleToken;
import just.khao.com.model.IssueTokenModel;
import just.khao.com.model.SignupModel;
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
