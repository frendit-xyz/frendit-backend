package frendit.xyz.com.entity.auth;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class SecureTokenEntity {
    private int id;
    private int auth_id;
    private String token;
    private Boolean verified;
    private Timestamp expires_at;
    private Timestamp created_at;
    private Timestamp updated_at;
}
