package com.example.demo.controller;

import com.example.demo.model.CreateTodoRequest;
import com.example.demo.model.Todo;
import com.example.demo.model.TodoUpdateRequest;
import com.example.demo.service.TodoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    private TodoController todoController;

    @BeforeEach
    void setUp() {
        todoController = new TodoController(todoService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build();
    }

    @Test
    void Todoを作成できる() throws Exception {

        Todo todo = new Todo(
                "123",
                "AWSを勉強する",
                false
        );

        when(todoService.create("AWSを勉強する"))
                .thenReturn(todo);

        mockMvc.perform(
                post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "AWSを勉強する"
                                }
                                """)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("123"))
        .andExpect(jsonPath("$.title").value("AWSを勉強する"))
        .andExpect(jsonPath("$.completed").value(false));

        verify(todoService, times(1))
                .create("AWSを勉強する");
    }

    @Test
    void Todoを全件取得できる() throws Exception {

        List<Todo> todos = List.of(
                new Todo("1", "AWS", false),
                new Todo("2", "Spring Boot", true)
        );

        when(todoService.findAll())
                .thenReturn(todos);

        mockMvc.perform(
                get("/todos")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].title").value("AWS"))
        .andExpect(jsonPath("$[1].title").value("Spring Boot"));

        verify(todoService, times(1))
                .findAll();
    }

    @Test
    void Todoを1件取得できる() throws Exception {

        Todo todo = new Todo(
                "123",
                "AWSを勉強する",
                false
        );

        when(todoService.findById("123"))
                .thenReturn(todo);

        mockMvc.perform(
                get("/todos/123")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("123"))
        .andExpect(jsonPath("$.title").value("AWSを勉強する"));

        verify(todoService, times(1))
                .findById("123");
    }

    @Test
    void 存在しないTodoは404を返す() throws Exception {

        when(todoService.findById("999"))
                .thenReturn(null);

        mockMvc.perform(
                get("/todos/999")
        )
        .andExpect(status().isNotFound());

        verify(todoService, times(1))
                .findById("999");
    }

    @Test
    void Todoを更新できる() throws Exception {

        Todo existingTodo = new Todo(
                "123",
                "AWSを勉強する",
                false
        );

        Todo updatedTodo = new Todo(
                "123",
                "AWSを完全に理解する",
                true
        );

        when(todoService.findById("123"))
                .thenReturn(existingTodo);

        when(todoService.update(
                "123",
                "AWSを完全に理解する",
                true
        )).thenReturn(updatedTodo);

        mockMvc.perform(
                put("/todos/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "AWSを完全に理解する",
                                    "completed": true
                                }
                                """)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("123"))
        .andExpect(jsonPath("$.title").value("AWSを完全に理解する"))
        .andExpect(jsonPath("$.completed").value(true));

        verify(todoService, times(1))
                .update(
                        "123",
                        "AWSを完全に理解する",
                        true
                );
    }

    @Test
    void Todoを削除できる() throws Exception {

        when(todoService.findById("123"))
                .thenReturn(
                        new Todo("123", "AWS", false)
                );

        mockMvc.perform(
                delete("/todos/123")
        )
        .andExpect(status().isNoContent());

        verify(todoService, times(1))
                .delete("123");
    }
}
