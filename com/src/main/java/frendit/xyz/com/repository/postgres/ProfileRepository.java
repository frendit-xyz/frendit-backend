package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.entity.profile.ProfileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileRepository {
    void createProfile(ProfileEntity profileEntity);

    ProfileEntity findByEmail(String email);

    ProfileEntity findByUsername(String username);
}
