package com.example.StateMachine.auth.entities;

import com.example.StateMachine.users.entities.User;
import lombok.Data;

@Data
public class LoginRTO {

    private User user;

    private String JWT;
}
