package com.mygroup.todoapi.dtos;

import com.mygroup.todoapi.entities.TodoEntity;
import com.mygroup.todoapi.models.Priority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TodoResponseDTO {
    private Long id;
    private String todoName;
    private String todoDescription;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoResponseDTO(TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.todoName = todoEntity.getTodoName();
        this.todoDescription = todoEntity.getTodoDescription();
        this.priority = todoEntity.getPriority();
        this.createdAt = todoEntity.getCreatedAt();
        this.updatedAt = todoEntity.getUpdatedAt();
    }
}
