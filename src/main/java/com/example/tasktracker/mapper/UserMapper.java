package com.example.tasktracker.mapper;

import com.example.tasktracker.api.v1.request.UserRq;
import com.example.tasktracker.api.v1.response.UserRs;
import com.example.tasktracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserRs> toDTO(List<User> list);

    UserRs toDTO(User user);

    User toModel(UserRq userRQ);

}
