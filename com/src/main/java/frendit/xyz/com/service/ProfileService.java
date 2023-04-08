package frendit.xyz.com.service;

import frendit.xyz.com.entity.AuthEntity;
import frendit.xyz.com.entity.GoogleToken;
import frendit.xyz.com.entity.ProfileEntity;
import frendit.xyz.com.repository.postgres.AuthRepository;
import frendit.xyz.com.repository.postgres.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileEntity findByEmail(String email){
        return profileRepository.findByEmail(email);
    }

    public ProfileEntity findByUsername(String username){
        return profileRepository.findByUsername(username);
    }

}
