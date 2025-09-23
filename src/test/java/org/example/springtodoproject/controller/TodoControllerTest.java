package org.example.springtodoproject.controller;

import org.example.springtodoproject.model.Status;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.repository.TodoRepository;
import org.example.springtodoproject.service.ChatGPTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void getTodoById() throws Exception {
        String todoId = "1";
        Todo todo = new Todo(todoId, "Test", Status.OPEN);
        todoRepository.save(todo);

        String response = """
                {
                "id": "1",
                "description": "Test",
                "status": "OPEN"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + todoId))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void addTodo() throws Exception {
        mockServer.expect(requestTo(ChatGPTService.BASE_URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        { "choices": [
                              {
                                 "message": {
                                   "content": "false"
                                 }
                              }
                            ]
                        }
                        """, MediaType.APPLICATION_JSON));

        String errorResponse = """
                {
                "message": "The text is incorrect: Test dezzcription"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "description": "Test dezzcription",
                                   "status": "DONE"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json(errorResponse));
    }
}