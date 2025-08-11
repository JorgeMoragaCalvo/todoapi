package com.mygroup.todoapi.models;

import lombok.Getter;

@Getter
public enum Priority {
    LOW(3),
    MEDIUM(2),
    HIGH(1);

    private final int value;

    Priority(int value) {
        this.value = value;
    }
}
