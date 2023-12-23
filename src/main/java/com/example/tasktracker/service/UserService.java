package com.example.tasktracker.service;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.reposirory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Mono<User> findByIdUser(String id){
        return userRepository.findById(id);
    }

    public Mono<User> saveUser(User user){
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public Mono<User> updateUser(String id, User user){
        return findByIdUser(id).flatMap(userForUpdate -> {
            if (user.getUserName() != null){
                userForUpdate.setUserName(user.getUserName());
            }
            if (user.getEmail() != null){
                userForUpdate.setEmail(user.getEmail());
            }
            return userRepository.save(userForUpdate);
        });
    }

    public Mono<Void> deleteByIdUser(String id){
        return userRepository.deleteById(id);
    }

}
