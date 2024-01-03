package com.clone.leetcode.auth.service;

import com.clone.leetcode.auth.dto.AuthResponse;
import com.clone.leetcode.auth.dto.LoginRequest;
import com.clone.leetcode.auth.dto.RegisterRequest;
import com.clone.leetcode.security.jwt.JwtService;
import com.clone.leetcode.system.exception.ResourceNotFoundException;
import com.clone.leetcode.user.converter.UserToUserDtoConverter;
import com.clone.leetcode.user.dto.UserDto;
import com.clone.leetcode.user.model.Role;
import com.clone.leetcode.user.model.User;
import com.clone.leetcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserToUserDtoConverter userToUserDtoConverter;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        UserDto userDto = userToUserDtoConverter.convert(savedUser);
        return new AuthResponse(userDto, jwt);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.email()));
        String jwt = jwtService.generateToken(user);
        UserDto userDto = userToUserDtoConverter.convert(user);
        return new AuthResponse(userDto, jwt);
    }
}
