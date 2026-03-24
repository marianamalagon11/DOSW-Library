package edu.eci.dows.tdd.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_l")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;

    private String name;
}