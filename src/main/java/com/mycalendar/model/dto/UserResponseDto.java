package com.mycalendar.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    Integer id;
    String name;
    @Email
    String email;
    String created_date;
    String updated_date;
}
