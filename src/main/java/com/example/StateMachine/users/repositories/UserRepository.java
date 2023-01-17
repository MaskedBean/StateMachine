package com.example.StateMachine.users.repositories;

import com.example.StateMachine.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    User getByActivationCode(String activationCode);

    User findByPasswordResetCode(String resetPasswordCode);

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM (\n" +
            "SELECT u.* ,  count(busyOrders.id) AS numberOfOrders\n" +
            "FROM users AS u\n" +
            "LEFT JOIN user_roles AS ur ON ur.user_id = u.id\n" +
            "LEFT JOIN ( SELECT * FROM \"orders\" WHERE \"status\" IN(4)) AS busyOrders ON busyOrders.rider_id = u.id\n" +
            "WHERE ur.role_id = 3 AND u.is_active = 1\n" +
            "GROUP BY u.id\n" +
            ") AS allRiders\n" +
            "WHERE allRiders, numberOfOrders = 0"+
            "LIMIT 1")
    Optional<User> pickRider();
}
