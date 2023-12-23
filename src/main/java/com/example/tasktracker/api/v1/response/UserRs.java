package com.example.tasktracker.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRs {

    private String id;

    private String userName;

    private String email;
}
