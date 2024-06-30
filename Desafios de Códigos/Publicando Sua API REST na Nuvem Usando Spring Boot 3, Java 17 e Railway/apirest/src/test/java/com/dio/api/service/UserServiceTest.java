package com.dio.api.service;

import com.dio.api.domain.model.User;
import com.dio.api.domain.repository.UserRepository;
import com.dio.api.dto.UserDTO;
import com.dio.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserMapper userMapper = mock(UserMapper.class);
    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);
    private final UserService userService = new UserService(userRepository, userMapper, emailService);

    @Test
    public void testRegisterUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setAccountNumber("12345");
        userDTO.setAgency("6789");
        userDTO.setBalance(1000.0);
        userDTO.setLimit(500.0);
        userDTO.setCardNumber("987654321");
        userDTO.setCardLimit(200.0);

        User user = new User();
        user.setName("John Doe");
        user.getAccount().setEmail("john.doe@example.com");

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.registerUser(userDTO);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        verify(emailService).sendWelcomeEmail(user);

        User capturedUser = captor.getValue();
        assertEquals("John Doe", capturedUser.getName());
        assertEquals("12345", capturedUser.getAccount().getNumber());
        assertEquals("6789", capturedUser.getAccount().getAgency());
        assertEquals(1000.0, capturedUser.getAccount().getBalance());
        assertEquals(500.0, capturedUser.getAccount().getLimit());
        assertEquals("987654321", capturedUser.getCard().getNumber());
        assertEquals(200.0, capturedUser.getCard().getLimit());
    }
}
