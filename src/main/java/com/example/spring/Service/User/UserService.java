package com.example.spring.Service.User;

import com.example.spring.Persistence.Model.Dto.JwtResponse;
import com.example.spring.Persistence.Model.Dto.UserDto;

public interface UserService {
    public JwtResponse register (UserDto userDto);

    JwtResponse authentication(UserDto userDto);
}
