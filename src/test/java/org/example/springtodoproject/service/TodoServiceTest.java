package org.example.springtodoproject.service;

import org.example.springtodoproject.model.Status;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TodoServiceTest {

    IdService idService =  mock(IdService.class);
    TodoRepository todoRepository = mock(TodoRepository.class);
    TodoService service = new TodoService(todoRepository, idService);

    @Test
    void upsert() {
        //GIVEN
        String id = UUID.randomUUID().toString();
        Mockito.when(idService.randomId()).thenReturn(id);
        Todo todo = new Todo(null,"test", Status.OPEN);
        //WHEN
        Todo newTodo = service.upsert(todo);
        //THEN
        Mockito.verify(idService).randomId();
        assertEquals(id, newTodo.id());
    }
}