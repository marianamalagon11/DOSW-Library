package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.UserDTO;
import edu.eci.dows.tdd.controller.mapper.UserMapper;
import edu.eci.dows.tdd.core.service.UserService;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO request) {
        User user = UserMapper.toModel(request);
        userService.addUser(user);
        return new ResponseEntity<>(UserMapper.toDTO(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream()
                        .map(UserMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } else {
            throw new UserNotFoundException("No existe el usuario con id: " + id);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new UserNotFoundException("No existe el usuario con id: " + id);
        }
    }
}