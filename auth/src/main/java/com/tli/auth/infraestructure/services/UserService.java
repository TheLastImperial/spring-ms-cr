package com.tli.auth.infraestructure.services;

import com.tli.auth.domain.entities.UserEntity;
import com.tli.auth.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        return getUserDetails(user);
    }

    private UserDetails getUserDetails(UserEntity user) {
        String[] roles = {"a", "b"};
        Set<GrantedAuthority> autorities = Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return new User(
            user.getEmail(),
            user.getPassword(),
            true,
            true,
            true,
            true,
                autorities
        );
    }
}
