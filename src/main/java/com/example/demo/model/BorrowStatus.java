package com.example.demo.model;

public enum BorrowStatus {
    BORROWED("已借出"),
    RETURNED("已归还"),
    OVERDUE("已逾期"),
    LOST("丢失");

    private final String description;

    BorrowStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 