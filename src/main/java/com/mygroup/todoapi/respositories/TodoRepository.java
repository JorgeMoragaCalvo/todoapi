package com.mygroup.todoapi.respositories;

import com.mygroup.todoapi.entities.TodoEntity;
import com.mygroup.todoapi.models.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findByPriority(Priority priority);

    @Query("select t from TodoEntity t where lower(t.todoName) like lower(concat('%', :name, '%'))")
    List<TodoEntity> findByTodoNameContainingIgnoreCase(String name);

    @Query("select t from TodoEntity t order by t.priority asc, t.createdAt desc")
    Page<TodoEntity> findAllOrderedByPriorityAndDate(Pageable pageable);
}
