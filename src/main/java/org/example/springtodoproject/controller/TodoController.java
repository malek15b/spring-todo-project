package org.example.springtodoproject.controller;

import lombok.AllArgsConstructor;
import org.example.springtodoproject.exception.ErrorMessage;
import org.example.springtodoproject.exception.SpellingException;
import org.example.springtodoproject.model.Todo;
import org.example.springtodoproject.model.TodoDto;
import org.example.springtodoproject.service.ChatGPTService;
import org.example.springtodoproject.service.TodoService;
import org.example.springtodoproject.service.UndoRedoStackService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UndoRedoStackService undoRedoStackService;
    private final ChatGPTService chatGPTService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody TodoDto todoDto) throws SpellingException {
        String text = todoDto.description();
        String res = chatGPTService.checkSpelling(text);

        if (!Boolean.parseBoolean(res)) {
            throw new SpellingException(text);
        }

        Todo todo = todoDto.toTodo(null);
        return todoService.upsert(todo);
    }

    @ExceptionHandler(SpellingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSpellingException(SpellingException e) {
        return new ErrorMessage(e.getMessage());
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable String id) {
        if (!todoService.exist(id)) {
            throw new NoSuchElementException("Id Not Found");
        }
        return todoService.get(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDto todoDto) {
        if (!todoService.exist(id)) {
            throw new NoSuchElementException("Id Not Found");
        }
        Todo todo = todoDto.toTodo(id);
        undoRedoStackService.init(todo);
        return todoService.upsert(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        if (!todoService.exist(id)) {
            throw new NoSuchElementException("Id Not Found");
        }
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
