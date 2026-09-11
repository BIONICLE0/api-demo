package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

@Component
public class DynamoDbConnectionTest {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbConnectionTest(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void test() {
        ListTablesResponse response = dynamoDbClient.listTables();

        System.out.println("DynamoDB tables: " + response.tableNames());
    }
}