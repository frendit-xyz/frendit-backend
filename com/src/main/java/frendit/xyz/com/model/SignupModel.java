package frendit.xyz.com.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupModel {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "First name is mandatory")
    private String first_name;
    @NotBlank(message = "Last name is mandatory")
    private String last_name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format must match")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    private String hashed_password;
    private boolean email_verified = false;
}
