package com.mygroup.todoapi.entities;

import com.mygroup.todoapi.models.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "todos")
@NoArgsConstructor
@Getter
@Setter
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String todoName;
    private String todoDescription;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.LOW;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public TodoEntity(String todoName, String todoDescription, Priority priority) {
        this.todoName = todoName;
        this.todoDescription = todoDescription;
        this.priority = priority != null ? priority : Priority.LOW;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        TodoEntity todoEntity = (TodoEntity) o;
        return Objects.equals(todoEntity.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
