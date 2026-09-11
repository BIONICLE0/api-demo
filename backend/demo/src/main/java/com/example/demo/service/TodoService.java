package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // CREATE
    public Todo create(String title) {

        Todo todo = new Todo(
                UUID.randomUUID().toString(),
                title,
                false
        );

        todoRepository.save(todo);

        return todo;
    }

    // READ 1件
    public Todo findById(String id) {
        return todoRepository.findById(id);
    }

    // READ 全件
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    // UPDATE
    public Todo update(String id, String title, boolean completed) {

        Todo todo = new Todo(
                id,
                title,
                completed
        );

        todoRepository.update(todo);

        return todo;
    }

    // DELETE
    public void delete(String id) {
        todoRepository.deleteById(id);
    }
}