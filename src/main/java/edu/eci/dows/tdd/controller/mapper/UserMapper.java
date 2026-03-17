package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.controller.dto.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName());
    }

    public static User toModel(UserDTO dto) {
        return new User(dto.getName(), dto.getId());
    }
}
