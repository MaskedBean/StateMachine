package com.example.StateMachine.order.entities;

public enum OrderStateEnum {
    CREATED,
    ACCEPTED,
    IN_PREPARATION,
    READY,
    DELIVERING,
    COMPLETED,
    CLOSED
}