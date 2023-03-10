package com.example.StateMachine.users.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
}