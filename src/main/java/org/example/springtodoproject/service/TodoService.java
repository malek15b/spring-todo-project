package org.example.springtodoproject.service;

import lombok.RequiredArgsConstructor;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final IdService idService;

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo upsert(Todo todo) {
        if (todo.id() == null) {
            todo = todo.withId(idService.randomId());
        }
        todoRepository.save(todo);
        return todo;
    }

    public Todo get(String id) {
        return todoRepository.findById(id).orElse(null);
    }
}
