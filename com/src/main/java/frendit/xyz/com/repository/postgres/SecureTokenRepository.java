package frendit.xyz.com.repository.postgres;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecureTokenRepository {
    String generateToken(String email);

    int verifySecureToken(String email, String token);
}
