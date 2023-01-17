package com.example.StateMachine.order.entities;

import com.example.StateMachine.users.entities.User;
import com.example.StateMachine.utils.entities.BaseEntitiy;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntitiy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;
    private String address;
    private String number;
    private String city;
    private String state;
    private OrderStateEnum status = OrderStateEnum.CREATED;
    private String zipCode;

    @ManyToOne
    private User restaurant;

    @ManyToOne
    private User rider;

    private double totalPrice;
}
