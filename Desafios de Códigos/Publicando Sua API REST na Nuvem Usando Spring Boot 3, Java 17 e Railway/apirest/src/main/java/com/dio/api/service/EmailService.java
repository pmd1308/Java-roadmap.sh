package com.dio.api.service;

import com.dio.api.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async("taskExecutor")
    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getAccount().getEmail());
        message.setSubject("Welcome to our service");
        message.setText("Dear " + user.getName() + ",\n\nThank you for registering with our service.\n\nBest regards,\nThe Team");
        mailSender.send(message);
    }
}
