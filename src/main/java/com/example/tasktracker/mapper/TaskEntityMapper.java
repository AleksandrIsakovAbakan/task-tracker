package com.example.tasktracker.mapper;

import com.example.tasktracker.api.v1.request.TaskRq;
import com.example.tasktracker.api.v1.response.TaskRs;
import com.example.tasktracker.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface TaskEntityMapper {

    TaskEntityMapper INSTANCE = Mappers.getMapper(TaskEntityMapper.class);

    List<TaskRs> toDTO(List<TaskEntity> list);

    TaskRs toDTO(TaskEntity taskEntity);

    TaskEntity toModel(TaskRq taskRq);
}
