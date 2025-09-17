package org.example.springtodoproject.model;

import lombok.With;

@With
public record TodoDto(String description, Status status) {
    public Todo toTodo(String id) {
        return new Todo(id, description, status);
    }
}
