package com.example.tasktracker.entity;

import com.example.tasktracker.api.v1.request.UserRq;
import com.example.tasktracker.api.v1.response.UserRs;
import com.example.tasktracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String userName;

    private String email;

    public static UserRs getUserRs(User user) { return UserMapper.INSTANCE.toDTO(user); }

    public static User getUserRq(UserRq userRq) { return UserMapper.INSTANCE.toModel(userRq); }
}
