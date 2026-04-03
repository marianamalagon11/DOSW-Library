package edu.eci.dows.tdd.security;

import edu.eci.dows.tdd.persistence.norelational.document.UserDocument;
import edu.eci.dows.tdd.persistence.norelational.repository.UserMongoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("mongo")
public class AppUserDetailsServiceMongo implements UserDetailsService {

    private final UserMongoRepository userRepository;

    public AppUserDetailsServiceMongo(UserMongoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found (mongo): " + username));


        String role = user.getRole() == null ? "USER" : user.getRole();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}