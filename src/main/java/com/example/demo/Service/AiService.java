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

        OpenAIRequest.Message message = new OpenAIRequest.Message();
        message.setRole("user");
        message.setContent(userMessage);

        OpenAIRequest request = new OpenAIRequest();
        request.setModel("openai/gpt-4o-mini"); 
        request.setMessages(List.of(message));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        headers.add("HTTP-Referer", "http://localhost:8089"); 
        headers.add("X-Title", "DemoApp");

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