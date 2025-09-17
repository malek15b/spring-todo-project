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
        Todo current = todoService.get(id);
        undoRedoService.setUndo(current);
        undoRedoService.setRedo(null);
        return todoService.upsert(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.remove(id);
    }

    @GetMapping("/undo")
    public Todo undo() {
        Todo undo = undoRedoService.getUndo();
        if (undo == null) {
            return null;
        }
        undoRedoService.setUndo(null);
        Todo current = todoService.get(undo.id());
        undoRedoService.setRedo(current);
        return todoService.upsert(undo);
    }

    @GetMapping("/redo")
    public Todo redo() {
        Todo redo = undoRedoService.getRedo();
        if (redo == null) {
            return null;
        }
        undoRedoService.setRedo(null);
        Todo current = todoService.get(redo.id());
        undoRedoService.setUndo(current);
        return todoService.upsert(redo);
    }
}
