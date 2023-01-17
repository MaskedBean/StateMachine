package com.example.StateMachine.utils.entities;

import com.example.StateMachine.users.entities.User;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntitiy {

    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User updatedBy;
}