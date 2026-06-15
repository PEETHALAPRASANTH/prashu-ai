package com.example.demo.Service;

import com.example.demo.dto.OpenAIRequest;
import com.example.demo.dto.OpenAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AiService {

	@Value("${openrouter.api.key}")
	private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(String userMessage) {

        String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

        OpenAIRequest.Message systemMessage = new OpenAIRequest.Message();
        systemMessage.setRole("system");
        systemMessage.setContent(
                "You are Prashu AI. You were created and developed by Prashu. " +
                "If anyone asks who created you, who developed you, who owns you, " +
                "or what AI you are, answer that you are Prashu AI created and developed by Prashu."
        );

        OpenAIRequest.Message userMessageObj = new OpenAIRequest.Message();
        userMessageObj.setRole("user");
        userMessageObj.setContent(userMessage);

        OpenAIRequest request = new OpenAIRequest();
        request.setModel("request.setModel(\"openai/gpt-4.1-mini\");");
        
        request.setMessages(List.of(systemMessage, userMessageObj));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        headers.add("HTTP-Referer", "https://prashu-ai-1.onrender.com");
        headers.add("X-Title", "Prashu-AI");

        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAIResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                OpenAIResponse.class
        );

        if (response.getBody() != null &&
                response.getBody().getChoices() != null &&
                !response.getBody().getChoices().isEmpty()) {

            return response.getBody()
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();
        }

        return "No response";
    }
}