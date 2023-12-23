package com.example.tasktracker.publisher;

import com.example.tasktracker.api.v1.response.TaskRs;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class TaskUpdatePublisher {

    private final Sinks.Many<TaskRs> taskUpdateSink;

    public TaskUpdatePublisher() {
        this.taskUpdateSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void publishTask(TaskRs taskRs){
        taskUpdateSink.tryEmitNext(taskRs);
    }

    public Sinks.Many<TaskRs> getUpdatesTaskSink(){
        return taskUpdateSink;
    }
}
