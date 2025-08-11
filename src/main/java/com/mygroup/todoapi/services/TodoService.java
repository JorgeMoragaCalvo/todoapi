package com.mygroup.todoapi.services;

import com.mygroup.todoapi.dtos.TodoResponseDTO;
import com.mygroup.todoapi.entities.TodoEntity;
import com.mygroup.todoapi.respositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDTO> getAllTodos(int skip, int limit) {
        validatePaginationParams(skip, limit);

        Pageable pageable = PageRequest.of(skip / limit, limit);
        Page<TodoEntity> todos = todoRepository.findAllOrderedByPriorityAndDate(pageable);

        return todos.getContent().stream()
                .map(TodoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodoResponseDTO getTodoById(Long todoId) {
        validateTodoId(todoId);

        TodoEntity todoEntity = todoRepository.findById(todoId)
                .orElseThrow();

        return new TodoResponseDTO(todoEntity);
    }

    private void validateTodoId(Long todoId) {
        if (todoId == null || todoId <= 0) {
            throw new IllegalArgumentException("Todo Id can't be null or must be positive");
        }
    }

    private void validatePaginationParams(int skip, int limit) {
        if (skip < 0) {
            throw new IllegalArgumentException("skip cannot be negative");
        }
        if (limit <= 0 || limit > 1000) {
            throw new IllegalArgumentException("limit must be between 1 and 1000");
        }
    }
}
