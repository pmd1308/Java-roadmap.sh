package com.spring.con.service.impl;

import com.spring.con.dto.UserDTO;
import com.spring.con.model.User;
import com.spring.con.repository.UserRepository;
import com.spring.con.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserDTO userDTO = new UserDTO("john_doe", "password", "john@example.com");
        User user = new User("john_doe", "encodedPassword", "john@example.com");
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.registerUser(userDTO);
        assertEquals("john_doe", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void testAuthenticateUser() {
        User user = new User("john_doe", "encodedPassword", "john@example.com");
        UserDTO userDTO = new UserDTO("john_doe", "password", "john@example.com");
        when(userRepository.findByUsername("john_doe")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("dummy-jwt-token");

        String token = userService.authenticateUser(userDTO);
        assertEquals("dummy-jwt-token", token);
    }
}
