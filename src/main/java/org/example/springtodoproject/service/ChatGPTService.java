package org.example.springtodoproject.service;

import org.example.springtodoproject.openai.OpenAiChoice;
import org.example.springtodoproject.openai.OpenAiRequest;
import org.example.springtodoproject.openai.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ChatGPTService {

    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private final RestClient restClient;
    private static final String MODEL_GPT5 = "gpt-4o-mini";

    public ChatGPTService(RestClient.Builder restClientBuilder,
                         @Value("${API_KEY}") String apikey) {
        this.restClient = restClientBuilder
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Bearer " + apikey)
                .build();
    }

    public String checkSpelling(String text) {
        String message = "Antworte nur mit 'true' oder 'false'. " +
                "Gib 'true' zurück, wenn der folgende Text keine Rechtschreibfehler enthält, " +
                "und 'false', wenn er mindestens einen Rechtschreibfehler enthält: '" + text + "'";

        OpenAiRequest request = this.getBody(message);
        return this.restClient.post()
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class).text();
    }

    private OpenAiRequest getBody(String message) {
        return new OpenAiRequest(MODEL_GPT5, message);
    }
}