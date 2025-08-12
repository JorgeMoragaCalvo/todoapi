package com.mygroup.todoapi.services;

import com.mygroup.todoapi.dtos.TodoCreateRequestDTO;
import com.mygroup.todoapi.dtos.TodoResponseDTO;
import com.mygroup.todoapi.dtos.TodoUpdatedRequestDTO;
import com.mygroup.todoapi.entities.TodoEntity;
import com.mygroup.todoapi.exceptions.TodoNotFoundException;
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

    public TodoResponseDTO createTodo(TodoCreateRequestDTO requestDTO) {
        TodoEntity todoEntity = new TodoEntity(
                requestDTO.getTodoName(),
                requestDTO.getTodoDescription(),
                requestDTO.getPriority()
        );

        TodoEntity savedEntity = todoRepository.save(todoEntity);
        return new TodoResponseDTO(savedEntity);
    }

    public TodoResponseDTO updateTodo(Long  todoId, TodoUpdatedRequestDTO updatedRequestDTO) {
        validateTodoId(todoId);
        TodoEntity todoEntity = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id " + todoId + " not found"));

        if (updatedRequestDTO.getTodoName() != null) todoEntity.setTodoName(updatedRequestDTO.getTodoName());
        if(updatedRequestDTO.getTodoDescription() != null) todoEntity.setTodoDescription(updatedRequestDTO.getTodoDescription());
        if(updatedRequestDTO.getPriority() != null) todoEntity.setPriority(updatedRequestDTO.getPriority());

        TodoEntity updatedEntity = todoRepository.save(todoEntity);
        return new TodoResponseDTO(updatedEntity);
    }

    public TodoResponseDTO deleteTodo(Long  todoId) {
        validateTodoId(todoId);
        TodoEntity todoEntity = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id " + todoId + " not found"));

        todoRepository.delete(todoEntity);
        return new TodoResponseDTO(todoEntity);
    }

    public long getTotalCount(){
        return todoRepository.count();
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
