package edu.eci.dows.tdd.persistence.relational.entity;

import edu.eci.dows.tdd.persistence.relational.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "user_l",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_l_username", columnNames = "username")
        }
)
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}