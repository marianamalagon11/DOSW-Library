package edu.eci.dows.tdd.security;

import edu.eci.dows.tdd.persistence.norelational.document.UserDocument;
import edu.eci.dows.tdd.persistence.norelational.repository.UserMongoRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Profile("mongo")
public class JwtAuthenticationFilterMongo extends JwtAuthFilter {

    private final JwtService jwtService;
    private final UserMongoRepository userRepository;

    public JwtAuthenticationFilterMongo(JwtService jwtService, UserMongoRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.validateAndGetClaims(token);
            String username = claims.getSubject();

            UserDocument user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                String role = user.getRole() == null ? "USER" : user.getRole();
                var authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}