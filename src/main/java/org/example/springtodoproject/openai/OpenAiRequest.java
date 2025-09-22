package org.example.springtodoproject.openai;

import java.util.List;

/**
 * {
 *     "model": "gpt-5", gpt-4o-mini
 *     "messages": [
 *       {
 *         "role": "developer",
 *         "content": "You are a helpful assistant."
 *       },
 *       {
 *         "role": "user",
 *         "content": "Hello!"
 *       }
 *     ]
 *   }
 */
public record OpenAiRequest(String model, List<OpenAiMessage> messages) {
    public OpenAiRequest(String model, String message) {
        this(model, List.of(new OpenAiMessage("user", message)));
    }
}
