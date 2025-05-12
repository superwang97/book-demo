package com.example.demo.model;

public enum BookStatus {
    AVAILABLE("可借阅"),
    BORROWED("已借出"),
    RESERVED("已预约"),
    MAINTENANCE("维修中"),
    LOST("丢失");

    private final String description;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 