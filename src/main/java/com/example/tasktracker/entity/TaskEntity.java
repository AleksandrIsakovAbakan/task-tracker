package com.example.tasktracker.entity;

import com.example.tasktracker.api.v1.request.TaskRq;
import com.example.tasktracker.api.v1.response.TaskRs;
import com.example.tasktracker.mapper.TaskEntityMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@Document(collection = "task")
@AllArgsConstructor
public class TaskEntity {

    @Id
    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds;

    @ReadOnlyProperty
    private User author;

    @ReadOnlyProperty
    private User assignee;

    @ReadOnlyProperty
    private Set<User> observers;

    public static TaskRs getTaskRs(TaskEntity taskEntity) {
        if(taskEntity != null) {
            return TaskEntityMapper.INSTANCE.toDTO(taskEntity);
        } else {
            return new TaskRs();
        }
    }

    public static TaskEntity getTaskRq(TaskRq taskRq) {
        return TaskEntityMapper.INSTANCE.toModel(taskRq);
    }

}
