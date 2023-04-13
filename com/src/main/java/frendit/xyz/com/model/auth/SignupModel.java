package frendit.xyz.com.model.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupModel {
    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = "^(?=.{4,50}$)(?![_.-])(?!.*[_.-]{2})[a-zA-Z0-9._-]+(?<![_.-])$", message = "Username should be alphanumeric")
    private String username;
    @NotBlank(message = "First name is mandatory")
    private String first_name;
    @NotBlank(message = "Last name is mandatory")
    private String last_name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format must match")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, max = 25, message = "Use stronger password")
    private String password;
    private String hashed_password;
    private boolean email_verified = false;
}
