package just.khao.com.service;

import just.khao.com.entity.AuthEntity;
import just.khao.com.entity.GoogleToken;
import just.khao.com.entity.ProfileEntity;
import just.khao.com.repository.postgres.AuthRepository;
import just.khao.com.repository.postgres.ProfileRepository;
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
