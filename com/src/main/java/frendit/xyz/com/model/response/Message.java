package frendit.xyz.com.model.response;

import lombok.Data;

@Data
public class Message {
    private String message;

    public Message(String message) {
        this.message = message;
    }
}