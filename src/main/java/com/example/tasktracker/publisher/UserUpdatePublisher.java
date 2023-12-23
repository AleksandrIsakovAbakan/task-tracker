package com.example.tasktracker.publisher;

import com.example.tasktracker.api.v1.response.UserRs;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class UserUpdatePublisher {

    private final Sinks.Many<UserRs> userUpdateSink;

    public UserUpdatePublisher() {
        this.userUpdateSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void publishUser(UserRs userRs){
        userUpdateSink.tryEmitNext(userRs);
    }

    public Sinks.Many<UserRs> getUpdatesUserSink(){
        return userUpdateSink;
    }
}
