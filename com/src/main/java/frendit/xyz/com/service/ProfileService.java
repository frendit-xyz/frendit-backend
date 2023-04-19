package frendit.xyz.com.service;

import frendit.xyz.com.entity.profile.ProfileEntity;
import frendit.xyz.com.entity.profile.update.UpdateProfileRequest;
import frendit.xyz.com.repository.postgres.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

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

    /**
     * Update profile according to the request body.
     *
     * @param currentUserEmail email of the user that currently logged in
     * @param request update request body
     * @return updated profile entity
     * @throws Exception when profile is not found searching by email
     */
    public ProfileEntity updateProfile (String currentUserEmail, UpdateProfileRequest request) throws Exception {
        ProfileEntity profile = profileRepository.findByEmail(currentUserEmail);

        if (profile == null) {
            throw new Exception("Cannot Find Profile");
        }

        profile.setFirst_name(request.first_name);
        profile.setLast_name(request.last_name);
        profile.setAvatar(request.avatar);
        profile.setContact_email(request.contact_email);
        profile.setUpdated_at(new Timestamp(new Date().getTime()));

        profileRepository.updateProfile(profile);

        return profile;
    }

}
