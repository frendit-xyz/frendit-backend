package frendit.xyz.com.model.response;

import lombok.Data;

import java.util.Map;

@Data
public class EmailData {
    private String to;
    private String subject;
    private String text;
    private String template;
    private Map<String, Object> properties;

}
