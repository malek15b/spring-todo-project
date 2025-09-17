package org.example.springtodoproject.service;

import lombok.Data;
import org.example.springtodoproject.model.Todo;
import org.springframework.stereotype.Service;

@Data
@Service
public class UndoRedoService {
    private Todo undo;
    private Todo redo;
}
