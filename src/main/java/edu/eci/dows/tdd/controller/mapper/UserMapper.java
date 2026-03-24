package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.controller.dto.UserDTO;
import edu.eci.dows.tdd.persistence.entity.UserEntity;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName());
    }
    public static User toModel(UserDTO dto) {
        return new User(dto.getId(), dto.getName());
    }
    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        return entity;
    }
    public static User toModel(UserEntity entity) {
        return new User(entity.getId(), entity.getName());
    }
}