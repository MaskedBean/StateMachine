package com.example.StateMachine.notifications.services;

import com.example.StateMachine.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendActivationEmail(User user){
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Ora sei iscritto alla piattaforma");
        sms.setText("Il codice di attivazione è: " + user.getActivationCode());
        mailSender.send(sms);
    }

    public void sendPasswordResetMail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Ora sei iscritto alla piattaforma");
        sms.setText("Il codice di attivazione è: " + user.getPasswordResetCode());
        mailSender.send(sms);
    }
}
