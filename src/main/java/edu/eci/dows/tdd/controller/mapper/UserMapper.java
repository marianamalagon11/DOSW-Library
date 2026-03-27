package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.controller.dto.UserDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.relational.entity.UserEntity;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getRole());
    }

    public static User toModel(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getRole());
    }

    public static User toModel(UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getUsername(), entity.getRole().name());
    }
}