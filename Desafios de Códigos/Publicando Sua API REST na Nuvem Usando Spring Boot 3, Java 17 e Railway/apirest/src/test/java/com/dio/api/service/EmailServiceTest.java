package com.dio.api.service;

import com.dio.api.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Test
    public void testSendWelcomeEmail() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailService emailService = new EmailService(mailSender);

        User user = new User();
        user.setName("John Doe");
        user.getAccount().setEmail("john.doe@example.com");

        emailService.sendWelcomeEmail(user);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertEquals("john.doe@example.com", message.getTo()[0]);
        assertEquals("Welcome to our service", message.getSubject());
        assertEquals("Dear John Doe,\n\nThank you for registering with our service.\n\nBest regards,\nThe Team", message.getText());
    }
}
