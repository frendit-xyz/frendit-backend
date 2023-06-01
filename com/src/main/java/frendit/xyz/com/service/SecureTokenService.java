package frendit.xyz.com.service;

import frendit.xyz.com.repository.postgres.SecureTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class SecureTokenService {
    private final SecureTokenRepository secureTokenRepository;

    public SecureTokenService(SecureTokenRepository secureTokenRepository) {
        this.secureTokenRepository = secureTokenRepository;
    }

    public String generateToken(String email){
        return secureTokenRepository.generateToken(email);
    }

    public boolean verifySecureToken(String email, String token){
        return secureTokenRepository.verifySecureToken(email, token) >= 1;
    }
}
