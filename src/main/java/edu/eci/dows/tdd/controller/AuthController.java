package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.LoginRequestDTO;
import edu.eci.dows.tdd.controller.dto.LoginResponseDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import edu.eci.dows.tdd.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepositoryPort userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepositoryPort userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            if (!auth.isAuthenticated()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());
            return new LoginResponseDTO(token);

        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}