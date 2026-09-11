package com.example.demo.repository;

import com.example.demo.model.Todo;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepository {

    private final DynamoDbClient dynamoDbClient;

    private static final String TABLE_NAME = "Todos";

    public TodoRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    // CREATE
    public void save(Todo todo) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", AttributeValue.builder()
                .s(todo.getId())
                .build());

        item.put("title", AttributeValue.builder()
                .s(todo.getTitle())
                .build());

        item.put("completed", AttributeValue.builder()
                .bool(todo.isCompleted())
                .build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    // READ 1件
    public Todo findById(String id) {

        Map<String, AttributeValue> key = Map.of(
                "id", AttributeValue.builder()
                        .s(id)
                        .build()
        );

        GetItemRequest request = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);

        if (!response.hasItem()) {
            return null;
        }

        Map<String, AttributeValue> item = response.item();

        return new Todo(
                item.get("id").s(),
                item.get("title").s(),
                item.get("completed").bool()
        );
    }

    // READ 全件
    public List<Todo> findAll() {

        ScanRequest request = ScanRequest.builder()
                .tableName(TABLE_NAME)
                .build();

        ScanResponse response = dynamoDbClient.scan(request);

        List<Todo> todos = new ArrayList<>();

        for (Map<String, AttributeValue> item : response.items()) {

            Todo todo = new Todo(
                    item.get("id").s(),
                    item.get("title").s(),
                    item.get("completed").bool()
            );

            todos.add(todo);
        }

        return todos;
    }

    // UPDATE
    public void update(Todo todo) {

        Map<String, AttributeValue> key = Map.of(
                "id", AttributeValue.builder()
                        .s(todo.getId())
                        .build()
        );

        Map<String, AttributeValue> values = Map.of(
                ":title", AttributeValue.builder()
                        .s(todo.getTitle())
                        .build(),
                ":completed", AttributeValue.builder()
                        .bool(todo.isCompleted())
                        .build()
        );

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .updateExpression("SET title = :title, completed = :completed")
                .expressionAttributeValues(values)
                .build();

        dynamoDbClient.updateItem(request);
    }

    // DELETE
    public void deleteById(String id) {

        Map<String, AttributeValue> key = Map.of(
                "id", AttributeValue.builder()
                        .s(id)
                        .build()
        );

        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        dynamoDbClient.deleteItem(request);
    }
}