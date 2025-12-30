package com.example.auth_application.services;

import com.example.auth_application.dtos.RegisterRequest;
import com.example.auth_application.dtos.RegisterResponse;
import com.example.auth_application.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);

    //login user


}
