package com.spring.con.util;

import com.spring.con.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtUtilTest {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Test
    public void testGenerateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setJwtSecret(jwtSecret);

        User user = new User("john_doe", "encodedPassword", "john@example.com");
        String token = jwtUtil.generateToken(user);

        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9")); // Checks if token starts with JWT header
    }

    @Test
    public void testGetUsernameFromToken() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setJwtSecret(jwtSecret);

        User user = new User("john_doe", "encodedPassword", "john@example.com");
        String token = jwtUtil.generateToken(user);
        String username = jwtUtil.getUsernameFromToken(token);

        assertEquals("john_doe", username);
    }

    @Test
    public void testValidateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setJwtSecret(jwtSecret);

        User user = new User("john_doe", "encodedPassword", "john@example.com");
        String token = jwtUtil.generateToken(user);

        assertTrue(jwtUtil.validateToken(token));
    }
}
