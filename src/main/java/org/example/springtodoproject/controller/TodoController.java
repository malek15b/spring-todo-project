package org.example.springtodoproject.controller;

import lombok.AllArgsConstructor;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.model.TodoDto;
import org.example.springtodoproject.service.TodoService;
import org.example.springtodoproject.service.UndoRedoService;
import org.example.springtodoproject.service.UndoRedoStackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UndoRedoStackService undoRedoStackService;

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
        undoRedoStackService.init(todo);
        return todoService.upsert(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.remove(id);
    }

    @GetMapping("/undo")
    public void undo() {
        undoRedoStackService.undo();
    }

    @GetMapping("/redo")
    public void redo() {
        undoRedoStackService.redo();
    }
}
