package com.example.demo.controller;

import com.example.demo.model.CreateTodoRequest;
import com.example.demo.model.Todo;
import com.example.demo.model.TodoUpdateRequest;
import com.example.demo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // POST /todos
    @PostMapping
    public Todo create(@RequestBody CreateTodoRequest request) {
        return todoService.create(request.title());
    }

    // GET /todos
    @GetMapping
    public List<Todo> findAll() {
        return todoService.findAll();
    }

    // GET /todos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Todo> findById(@PathVariable String id) {

        Todo todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(todo);
    }

    // PUT /todos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(
            @PathVariable String id,
            @RequestBody TodoUpdateRequest request) {

        Todo todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        Todo updated = todoService.update(
                id,
                request.title(),
                request.completed()
        );

        return ResponseEntity.ok(updated);
    }

    // DELETE /todos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        Todo todo = todoService.findById(id);

        if (todo == null) {
            return ResponseEntity.notFound().build();
        }

        todoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}