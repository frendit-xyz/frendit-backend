package frendit.xyz.com.model.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SigninModel {
    private String username;
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
