package org.example.springtodoproject.controller;

import lombok.AllArgsConstructor;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.model.TodoDto;
import org.example.springtodoproject.service.TodoService;
import org.example.springtodoproject.service.UndoRedoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UndoRedoService undoRedoService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody TodoDto todoDto) {
        Todo todo = todoDto.toTodo(null);
        return todoService.upsert(todo);
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id) {
        return todoService.get(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDto todoDto) {
        Todo todo = todoDto.toTodo(id);
        undoRedoService.init(todo);
        return todoService.upsert(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.remove(id);
    }

    @GetMapping("/undo")
    public void undo() {
        undoRedoService.undo();
    }

    @GetMapping("/redo")
    public void redo() {
        undoRedoService.redo();
    }
}
