package com.example.tasktracker.reposirory;

import com.example.tasktracker.entity.TaskEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<TaskEntity, String> {
}
