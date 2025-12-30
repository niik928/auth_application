package com.example.auth_application.serviceImpl;

import com.example.auth_application.dtos.RegisterRequest;
import com.example.auth_application.dtos.RegisterResponse;
import com.example.auth_application.dtos.UserDto;
import com.example.auth_application.entities.Provider;
import com.example.auth_application.entities.Role;
import com.example.auth_application.entities.User;
import com.example.auth_application.repositories.RoleRepository;
import com.example.auth_application.repositories.UserRepository;
import com.example.auth_application.services.AuthService;
import com.example.auth_application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .image(userDto.getImage())
                .enable(true)
                .provider(Provider.LOCAL)
                .role(new HashSet<>())   // ⭐ MOST IMPORTANT
                .build();

        user.getRole().add(userRole);

        User savedUser = userRepository.save(user);

        return UserDto.builder()
                .ID(savedUser.getID())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .image(savedUser.getImage())
                .enable(savedUser.isEnable())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .provider(savedUser.getProvider())
                .build();
    }


//    @Override
//    public UserDto registerUser(UserDto userDto) {
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        return userService.createUser(userDto);
//
//    }


}
