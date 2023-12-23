package com.example.tasktracker.api.v1.response;

import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRs {

    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds;

    private User author;

    private User assignee;

    private Set<User> observers;
}
