package com.example.tasktracker.api.v1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRq {

    private String id;

    private String userName;

    private String email;
}
