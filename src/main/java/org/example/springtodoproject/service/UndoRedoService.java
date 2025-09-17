package org.example.springtodoproject.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.springtodoproject.model.Todo;
import org.springframework.stereotype.Service;

@Data
@Service
public class UndoRedoService {
    private Todo undo;
    private Todo redo;
    private final TodoService todoService;

    public void init(Todo todo) {
        undo = todoService.get(todo.id());
        redo = null;
    }

    public void undo() {
        if (undo == null) {
            return;
        }
        redo = todoService.get(undo.id());
        todoService.upsert(undo);
        undo = null;
    }

    public void redo() {
        if (redo == null) {
            return;
        }
        undo = todoService.get(redo.id());
        todoService.upsert(redo);
        redo = null;
    }
}
