package com.example.spring.Controller;

import com.example.spring.Persistence.Model.Dto.JwtResponse;
import com.example.spring.Persistence.Model.Dto.UserDto;
import com.example.spring.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("authentication")
    public ResponseEntity<JwtResponse> authentication (@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.authentication(userDto));
    }
    @PostMapping("register")
    public ResponseEntity<JwtResponse> register (@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.register(userDto));
    }
}
