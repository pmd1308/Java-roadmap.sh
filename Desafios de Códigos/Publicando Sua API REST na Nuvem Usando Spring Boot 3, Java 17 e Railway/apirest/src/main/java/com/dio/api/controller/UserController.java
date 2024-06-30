package com.dio.api.controller;

import com.dio.api.dto.UserDTO;
import com.dio.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.list;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserDTO createUser(@Validated @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}