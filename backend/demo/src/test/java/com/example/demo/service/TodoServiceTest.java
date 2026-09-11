package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void Todoを作成できる() {

        Todo result =
                todoService.create("AWSを勉強する");

        assertNotNull(result.getId());
        assertEquals(
                "AWSを勉強する",
                result.getTitle()
        );
        assertFalse(result.isCompleted());

        verify(todoRepository, times(1))
                .save(any(Todo.class));
    }

    @Test
    void Todoを1件取得できる() {

        Todo todo = new Todo(
                "123",
                "AWSを勉強する",
                false
        );

        when(todoRepository.findById("123"))
                .thenReturn(todo);

        Todo result =
                todoService.findById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
        assertEquals(
                "AWSを勉強する",
                result.getTitle()
        );
        assertFalse(result.isCompleted());

        verify(todoRepository, times(1))
                .findById("123");
    }

    @Test
    void 存在しないTodoはnullを返す() {

        when(todoRepository.findById("999"))
                .thenReturn(null);

        Todo result =
                todoService.findById("999");

        assertNull(result);
    }

    @Test
    void Todoを全件取得できる() {

        List<Todo> todos = List.of(
                new Todo("1", "AWS", false),
                new Todo("2", "Spring Boot", true)
        );

        when(todoRepository.findAll())
                .thenReturn(todos);

        List<Todo> result =
                todoService.findAll();

        assertEquals(2, result.size());
        assertEquals("AWS", result.get(0).getTitle());
        assertEquals("Spring Boot", result.get(1).getTitle());

        verify(todoRepository, times(1))
                .findAll();
    }

    @Test
    void Todoを更新できる() {

        Todo result =
                todoService.update(
                        "123",
                        "AWSを完全に理解する",
                        true
                );

        assertEquals("123", result.getId());
        assertEquals(
                "AWSを完全に理解する",
                result.getTitle()
        );
        assertTrue(result.isCompleted());

        verify(todoRepository, times(1))
                .update(any(Todo.class));
    }

    @Test
    void Todoを削除できる() {

        todoService.delete("123");

        verify(todoRepository, times(1))
                .deleteById("123");
    }
}