package com.example.demo.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

    @Bean
    @Profile("local")
    public DynamoDbClient localDynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(
                        URI.create("http://dynamodb:8000")
                )
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("dummy", "dummy")
                        )
                )
                .build();
    }

    @Bean
    @Profile("aws")
    public DynamoDbClient awsDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .build();
    }
}