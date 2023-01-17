package com.example.StateMachine.order.controllers;

import com.example.StateMachine.order.entities.Order;
import com.example.StateMachine.order.entities.OrderDTO;
import com.example.StateMachine.order.repository.OrdersRepository;
import com.example.StateMachine.order.services.OrderService;
import com.example.StateMachine.users.entities.User;
import com.example.StateMachine.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    public ResponseEntity<Order> create(@RequestBody OrderDTO order) throws Exception {
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (Roles.hasRole(user, Roles.REGISTERED)) {
            return ResponseEntity.ok(ordersRepository.findByCreatedBy(user));
        }
        else if (Roles.hasRole(user, Roles.RIDER)) {
            return ResponseEntity.ok(ordersRepository.findByRider(user));
        }
        else if (Roles.hasRole(user, Roles.RESTAURANT)) {
            return ResponseEntity.ok(ordersRepository.findByRestaurant(user));
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getSingle(@PathVariable Long id, Principal principal){
        Optional<Order> order = ordersRepository.findById(id);

        if(!order.isPresent()) return ResponseEntity.notFound().build();
        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(Roles.hasRole(user,Roles.REGISTERED) && order.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        }
        else if (Roles.hasRole(user,Roles.RESTAURANT) && order.get().getRestaurant().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        }
        else if (Roles.hasRole(user,Roles.RIDER) && order.get().getRider().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        }

        return ResponseEntity.notFound().build();
    }
}
