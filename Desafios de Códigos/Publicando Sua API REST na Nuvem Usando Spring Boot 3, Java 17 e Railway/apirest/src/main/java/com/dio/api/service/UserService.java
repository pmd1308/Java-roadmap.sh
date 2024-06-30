package com.dio.api.service;

import com.dio.api.domain.model.User;
import com.dio.api.domain.repository.UserRepository;
import com.dio.api.dto.UserDTO;
import com.dio.api.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Transactional
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new NoSuchElementException("Could not find user"));
        return userMapper.toDto(user);
    }

    @Transactional
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                   .map(userMapper::toDto)
                   .collect(Collectors.toList());
    }

    @Transactional
    public void registerUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user);
    }
}