package org.example.springtodoproject.controller;

import lombok.AllArgsConstructor;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos(){
        return todoService.getTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        return todoService.upsert(todo);
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id){
        return todoService.get(id);
    }
}
