package com.example.spring.Config;

import com.example.spring.Persistence.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    private final UserRepository userRepository;

    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow((() -> new UsernameNotFoundException("Email not found..")));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService());
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
