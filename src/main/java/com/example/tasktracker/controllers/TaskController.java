package com.example.tasktracker.controllers;

import com.example.tasktracker.api.v1.request.TaskRq;
import com.example.tasktracker.api.v1.response.TaskRs;
import com.example.tasktracker.entity.TaskEntity;
import com.example.tasktracker.publisher.TaskUpdatePublisher;
import com.example.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskUpdatePublisher taskUpdatePublisher;


    @GetMapping
    public Flux<TaskRs> getAllTasks(){
        return taskService.findAllTasks().map(TaskEntity::getTaskRs).cache();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskRs>> getByIdTask(@PathVariable String id){
        return taskService.getByIdTask(id)
                .map(TaskEntity::getTaskRs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build()).cache();
    }

    @PostMapping
    public Mono<ResponseEntity<TaskRs>> createTask(@RequestBody TaskRq taskRq){
        return taskService.saveTask(TaskEntity.getTaskRq(taskRq))
                .map(TaskEntity::getTaskRs)
                .doOnSuccess(taskUpdatePublisher::publishTask)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskRs>> putTask(@PathVariable String id, @RequestBody TaskRq taskRq){
        return taskService.updateTask(id, TaskEntity.getTaskRq(taskRq))
                .map(TaskEntity::getTaskRs)
                .doOnSuccess(taskUpdatePublisher::publishTask)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/observersAdd/{id}")
    public Mono<ResponseEntity<TaskRs>> getTaskObservers(@PathVariable String id, @RequestParam String idObservers){
        return taskService.updateTaskObservers(id, idObservers)
                .map(TaskEntity::getTaskRs)
                .doOnSuccess(taskUpdatePublisher::publishTask)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id){
        return taskService.deleteByIdTask(id).then(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TaskRs>> getTaskUpdate(){
        return taskUpdatePublisher.getUpdatesTaskSink()
                .asFlux()
                .map(taskRs -> ServerSentEvent.<TaskRs>builder(taskRs).build());
    }
}
