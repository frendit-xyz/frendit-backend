package frendit.xyz.com.entity.auth;

import lombok.Data;

@Data
public class GoogleToken {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    private boolean email_verified;
    private boolean valid;

    public boolean isValid() {
        if(sub == null || name == null || given_name == null || family_name == null || email == null){
            return false;
        } else {
            return true;
        }
    }
}
