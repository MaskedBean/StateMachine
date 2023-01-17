package com.example.StateMachine.auth.controllers;

import com.example.StateMachine.auth.entities.SignUpActivationDTO;
import com.example.StateMachine.auth.entities.SignUpDTO;
import com.example.StateMachine.auth.services.SignUpService;
import com.example.StateMachine.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup/{role}")
    public User signUp(@RequestBody SignUpDTO signUpDTO, @PathVariable String role)throws Exception{
        return signUpService.signUp(signUpDTO, role);
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
        signUpService.signUp(signUpDTO);
    }

    @PostMapping("/signup/activation")
    public void activate(@RequestBody SignUpActivationDTO signUpActivationDTO) throws Exception {
        signUpService.activate(signUpActivationDTO);
    }
}
