package com.mygroup.todoapi.dtos;

import com.mygroup.todoapi.models.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoUpdatedRequestDTO {

    private String todoName;
    private String todoDescription;
    private Priority priority;
}
