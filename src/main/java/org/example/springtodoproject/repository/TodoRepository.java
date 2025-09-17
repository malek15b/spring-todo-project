package org.example.springtodoproject.repository;

import org.example.springtodoproject.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

}
