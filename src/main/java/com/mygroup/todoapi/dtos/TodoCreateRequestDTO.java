package com.mygroup.todoapi.dtos;

import com.mygroup.todoapi.models.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TodoCreateRequestDTO {

    @NotBlank(message = "Todo name is required")
    @Size(min = 3, max = 512, message = "Todo name must be between 3 and 512 chars")
    private String todoName;

    @NotBlank(message = "Todo description is required")
    private String todoDescription;

    private Priority priority = Priority.LOW;

    public TodoCreateRequestDTO(String todoName, String todoDescription, Priority priority) {
        this.todoName = todoName;
        this.todoDescription = todoDescription;
        this.priority = priority != null ? priority : Priority.LOW;
    }
}
