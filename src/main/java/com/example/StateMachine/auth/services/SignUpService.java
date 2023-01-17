package com.example.StateMachine.auth.services;

import com.example.StateMachine.auth.entities.SignUpActivationDTO;
import com.example.StateMachine.auth.entities.SignUpDTO;
import com.example.StateMachine.notifications.services.MailNotificationService;
import com.example.StateMachine.users.entities.Role;
import com.example.StateMachine.users.entities.User;
import com.example.StateMachine.users.repositories.RoleRepository;
import com.example.StateMachine.users.repositories.UserRepository;
import com.example.StateMachine.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;


    public User signUp (SignUpDTO signUpDTO, String role) throws Exception {
        User userFromDB = userRepository.findByEmail(signUpDTO.getEmail());
        if(userFromDB != null) throw new Exception("User already exists");
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setSurname(signUpDTO.getSurname());
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);

        return userRepository.save(user);
    }

    public User activate(SignUpActivationDTO signUpActivationDTO) throws Exception {
        User user = userRepository.getByActivationCode(signUpActivationDTO.getActivationCode());
        if (user == null) throw new Exception("L'utente non esiste");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }

    public User signUp(SignUpDTO signUpDTO) throws Exception {
        return this.signUp(signUpDTO,Roles.REGISTERED);
    }
}

