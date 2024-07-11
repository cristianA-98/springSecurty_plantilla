package com.example.spring.Service.User;


import com.example.spring.Config.Jwt.JwtService;
import com.example.spring.Persistence.Entity.User;
import com.example.spring.Persistence.Model.Dto.JwtResponse;
import com.example.spring.Persistence.Model.Dto.UserDto;
import com.example.spring.Persistence.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponse authentication(UserDto userDto) {

     Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(),userDto.getPassword()
        ));


        return JwtResponse.builder()
                .jwt( jwtService.getToken(authentication.getName()))
                .build();
    }



    public JwtResponse register (UserDto userDto){

        if(userRepository.findByEmail(userDto.getEmail()).isPresent())   return JwtResponse.builder().jwt( "Email in Used").build();

        userRepository.save(
                User.builder()
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .email(userDto.getEmail())
                        .rol(userDto.getRol())
                        .build()
        );

        return JwtResponse.builder()
                .jwt( jwtService.getToken(userDto.getEmail()))
                .build();
    }


}
