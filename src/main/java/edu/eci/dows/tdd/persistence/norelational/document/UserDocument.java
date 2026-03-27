package edu.eci.dows.tdd.persistence.nonrelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class UserDocument {

    @Id
    private String id;

    private String name;
    private String username;
    private String password;

    private String role;
    private String email;
    private String membershipType;
    private String addedAt;
}