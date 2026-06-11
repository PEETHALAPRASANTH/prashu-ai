package com.example.demo.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class OpenAIRequest {

    private String model;
    private List<Message> messages;
    private Map<String, String> response_format;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}