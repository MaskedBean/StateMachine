package com.example.StateMachine.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.StateMachine.auth.entities.LoginDTO;
import com.example.StateMachine.auth.entities.LoginRTO;
import com.example.StateMachine.users.entities.User;
import com.example.StateMachine.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LoginService {

    public static final String JWT_SECRET = "ca0c4b48-22b1-48f5-8a06-fed58581679f";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public LoginRTO login(LoginDTO loginDTO){
        if (loginDTO == null) return null;
        User userFromDB = userRepository.findByEmail(loginDTO.getEmail());
        if (userFromDB == null || !userFromDB.isActive()) return null;
        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if (!canLogin) return null;

        String JWT = getJWT(userFromDB);
        userFromDB.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(userFromDB);

        userFromDB.setPassword(null);
        LoginRTO out = new LoginRTO();
        out.setJWT(JWT);
        out.setUser(userFromDB);

        return out;
    }

    public boolean canUserLogin(User user, String password){
        return passwordEncoder.matches(password, user.getPassword());
    }

    public static String getJWT(User user){
        Date expireAt = Timestamp.valueOf(LocalDateTime.now().plusDays(15));
        return JWT.create().withIssuer("LoginDemo")
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(expireAt)
                .withClaim("id",user.getId())
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }
}
