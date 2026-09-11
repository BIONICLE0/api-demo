package com.example.demo.model;

public record TodoUpdateRequest(
        String title,
        boolean completed
) {
}