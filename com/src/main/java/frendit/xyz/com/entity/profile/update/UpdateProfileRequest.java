package frendit.xyz.com.entity.profile.update;

import lombok.Data;

/**
 * Body of the Profile Update Request
 */
@Data
public class UpdateProfileRequest {
    public String first_name;

    public String last_name;

    public String avatar;

    public String contact_email;
}
