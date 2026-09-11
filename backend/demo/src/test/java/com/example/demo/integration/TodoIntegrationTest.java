package com.example.demo.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createGetUpdateDeleteTodo() throws Exception {

        // =========================
        // 1. POST - Todo作成
        // =========================
        String response = mockMvc.perform(
                post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "AWSを勉強する"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("AWSを勉強する"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // レスポンスからIDを取得
        String id = com.jayway.jsonpath.JsonPath
                .parse(response)
                .read("$.id", String.class);

        // =========================
        // 2. GET - Todo取得
        // =========================
        mockMvc.perform(
                get("/todos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("AWSを勉強する"));

        // =========================
        // 3. PUT - Todo更新
        // =========================
        mockMvc.perform(
                put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "AWSをもっと勉強する",
                                    "completed": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("AWSをもっと勉強する"));

        // =========================
        // 4. DELETE - Todo削除
        // =========================
        mockMvc.perform(
                delete("/todos/{id}", id))
                .andExpect(status().isNoContent());

        // =========================
        // 5. 削除後GET
        // =========================
        mockMvc.perform(
                get("/todos/{id}", id))
                .andExpect(status().isNotFound());
    }
}