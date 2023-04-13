package frendit.xyz.com.model.auth;

import lombok.Data;

@Data
public class TokenModel {
    private String access_token;
    private String refresh_token;
}
