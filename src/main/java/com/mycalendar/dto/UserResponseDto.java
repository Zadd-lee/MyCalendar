package com.mycalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    Integer id;
    String name;
    String email;
    String created_date;
    String updated_date;
}
