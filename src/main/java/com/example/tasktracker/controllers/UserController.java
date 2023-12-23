package com.example.tasktracker.controllers;

import com.example.tasktracker.api.v1.request.UserRq;
import com.example.tasktracker.api.v1.response.UserRs;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.publisher.UserUpdatePublisher;
import com.example.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserUpdatePublisher userUpdatePublisher;

    @GetMapping
    public Flux<UserRs> getAllUsers(){
        return userService.findAllUsers().map(User::getUserRs);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserRs>> getByIdUser(@PathVariable String id){
        return userService.findByIdUser(id).map(User::getUserRs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserRs>> createUser(@RequestBody UserRq userRq){
        return userService.saveUser(User.getUserRq(userRq))
                .map(User::getUserRs)
                .doOnSuccess(userUpdatePublisher::publishUser)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserRs>> putUser(@PathVariable String id, @RequestBody UserRq userRq){
        return userService.updateUser(id, User.getUserRq(userRq))
                .map(User::getUserRs)
                .doOnSuccess(userUpdatePublisher::publishUser)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id){
        return userService.deleteByIdUser(id).then(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<UserRs>> getUserUpdate(){
        return userUpdatePublisher.getUpdatesUserSink()
                .asFlux()
                .map(userRs -> ServerSentEvent.<UserRs>builder(userRs).build());
    }
}
