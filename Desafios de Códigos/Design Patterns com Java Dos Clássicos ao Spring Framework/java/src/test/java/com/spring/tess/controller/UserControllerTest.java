package com.spring.con.controller;

import com.spring.con.dto.UserDTO;
import com.spring.con.service.UserService;
import com.spring.con.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, jwtUtil)).build();
    }

    @Test
    public void testAuthenticate() throws Exception {
        when(userService.authenticateUser(any(UserDTO.class))).thenReturn("dummy-jwt-token");

        mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john_doe\",\"password\":\"password\"}"))
            .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO("john_doe", "password", "john@example.com");
        when(userService.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john_doe\",\"password\":\"password\",\"email\":\"john@example.com\"}"))
            .andExpect(status().isCreated());
    }
}
