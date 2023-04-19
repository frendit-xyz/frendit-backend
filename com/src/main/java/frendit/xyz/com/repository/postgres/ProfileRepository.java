package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.entity.profile.ProfileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileRepository {
    void createProfile(ProfileEntity profileEntity);

    ProfileEntity findByEmail(String email);

    ProfileEntity findByUsername(String username);

    /**
     * Update profile according to the request body.
     *
     * @param profileEntity profile to be updated
     */
    void updateProfile(ProfileEntity profileEntity);
}
