package com.example.demo.model;

public enum ReservationStatus {
    PENDING("待处理"),
    APPROVED("已批准"),
    REJECTED("已拒绝"),
    CANCELLED("已取消"),
    COMPLETED("已完成");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 