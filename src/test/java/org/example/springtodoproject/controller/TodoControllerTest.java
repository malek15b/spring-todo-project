package org.example.springtodoproject.controller;

import org.example.springtodoproject.model.Status;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void getTodoById() throws Exception {
        String todoId = "1";
        Todo todo = new Todo(todoId,"Test", Status.OPEN);
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
}