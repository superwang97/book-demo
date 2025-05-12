package com.example.demo.model;

public enum UserRole {
    ADMIN("管理员"),
    LIBRARIAN("图书管理员"),
    READER("读者");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 