package edu.eci.dows.tdd.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String id;
    String name;
    String username;
    String password;
    String role;

    // para la no relacional
    private String email;
    private String membershipType;
    private String addedAt;
}