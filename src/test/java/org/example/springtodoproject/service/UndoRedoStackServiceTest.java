package org.example.springtodoproject.service;

import org.example.springtodoproject.model.Status;
import org.example.springtodoproject.model.Todo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UndoRedoStackServiceTest {

    private final TodoService todoService = Mockito.mock(TodoService.class);
    private final UndoRedoStackService undoRedoStackService =
            new UndoRedoStackService(todoService);

    @Test
    void testUndoRedo() {
        Todo todo1 = new Todo("1", "Test1", Status.OPEN);
        Todo todo2 = new Todo("1", "Test2", Status.DONE);
        Todo todo3 = new Todo("1", "Test3", Status.IN_PROGRESS);
        Todo todo4 = new Todo("1", "Test4", Status.DONE);
        when(todoService.get("1")).
                thenReturn(todo1).thenReturn(todo2).thenReturn(todo3).thenReturn(todo4);

        undoRedoStackService.init(todo1);
        undoRedoStackService.init(todo2);
        undoRedoStackService.init(todo3);
        undoRedoStackService.init(todo4);

        undoRedoStackService.undo();//Test4
        undoRedoStackService.undo();//Test3
        undoRedoStackService.redo();//Test4
        undoRedoStackService.undo();//Test3
        undoRedoStackService.undo();//Test2

        Todo current = undoRedoStackService.getCurrentTodo();

        assertEquals("Test2", current.description());
        assertEquals(Status.DONE, current.status());
    }
}