package org.example.springtodoproject.model;

import lombok.With;

@With
public record Todo(String id, String description, Status status) {
}
