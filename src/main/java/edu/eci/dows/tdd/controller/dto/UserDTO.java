package edu.eci.dows.tdd.controller.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String username;
    private String role;
}
