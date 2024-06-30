package com.dio.api.mapper;

import com.dio.api.domain.model.User;
import com.dio.api.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.getAccount().setNumber(dto.getAccountNumber());
        user.getAccount().setAgency(dto.getAgency());
        user.getAccount().setBalance(dto.getBalance());
        user.getAccount().setLimit(dto.getLimit());
        user.getCard().setNumber(dto.getCardNumber());
        user.getCard().setLimit(dto.getCardLimit());
        return user;
    }

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAccountNumber(user.getAccount().getNumber());
        dto.setAgency(user.getAccount().getAgency());
        dto.setBalance(user.getAccount().getBalance());
        dto.setLimit(user.getAccount().getLimit());
        dto.setCardNumber(user.getCard().getNumber());
        dto.setCardLimit(user.getCard().getLimit());
        return dto;
    }
}
