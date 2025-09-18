package org.example.springtodoproject.service;

import lombok.Getter;
import org.example.springtodoproject.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class UndoRedoStackService {
    private final Deque<Todo> undoStack = new ArrayDeque<>();
    private final Deque<Todo> redoStack = new ArrayDeque<>();
    private final TodoService todoService;

    @Getter
    private Todo currentTodo;

    public UndoRedoStackService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void init(Todo todo) {
        undoStack.push(todoService.get(todo.id()));
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Todo current = todoService.get(undoStack.peek().id());
        redoStack.push(current);

        Todo previous = undoStack.pop();
        this.currentTodo = previous;
        todoService.upsert(previous);
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Todo current = todoService.get(redoStack.peek().id());
        undoStack.push(current);

        Todo next = redoStack.pop();
        this.currentTodo = next;
        todoService.upsert(next);
    }

}
