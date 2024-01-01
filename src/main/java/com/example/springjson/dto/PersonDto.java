package com.example.springjson.dto;

import com.example.springjson.annotation.CheckEmail;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel()
public class PersonDto extends AbstractDto<Long> {
    @NotNull
    private Long pk;
    private Long id;
    private String firstName;
    private String lastName;
    @CheckEmail
    private String email;
    private String gender;
    private String ipAddress;
    private String country;
}