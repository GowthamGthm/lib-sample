package com.library.management.service;

import com.library.management.entity.User;
import com.library.management.payload.AuthResponse;
import com.library.management.repository.UserRepository;
import com.library.management.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthResponse login(String username, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtTokenProvider.generateToken(user);

        return new AuthResponse(token, user.getUsername(), user.getFullName(), user.getEmail());
    }

    public AuthResponse validateSession(String token) {

        if (!token.startsWith("Bearer ")) {
            throw new InvalidOneTimeTokenException("Invalid token format. Expected format; Bearer <token>");
        }
        String[] tokenParts = token.split(" ");
        jwtTokenProvider.validateToken(tokenParts[1]);
        String userName = jwtTokenProvider.getUsernameFromToken(tokenParts[1]);

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found/ Not a valid session"));

        return new AuthResponse(tokenParts[1], user.getUsername(), user.getFullName(), user.getEmail());


    }

}