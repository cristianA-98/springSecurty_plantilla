package com.example.spring.Persistence.Model.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;
    @NotEmpty(message = "Email Empty")
    @Email(message = "Email not valid")
    private String email;

    @NotEmpty(message = "Password Empty")
    @Length( min = 7 , max = 30, message = "Password min 8 and 30 max characters ")
    private String password;
    private String rol;

}
