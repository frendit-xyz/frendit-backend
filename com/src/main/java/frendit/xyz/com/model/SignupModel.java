package frendit.xyz.com.model;

import lombok.Data;

@Data
public class SignupModel {
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String hashed_password;
    private boolean email_verified = false;
}
