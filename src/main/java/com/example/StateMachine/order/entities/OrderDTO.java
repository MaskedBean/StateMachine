package com.example.StateMachine.order.entities;

import com.example.StateMachine.users.entities.User;
import lombok.Data;


@Data
public class OrderDTO {


    private String description;
    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;
    private Long restaurant;
    private User rider;
    private double totalPrice;
}
