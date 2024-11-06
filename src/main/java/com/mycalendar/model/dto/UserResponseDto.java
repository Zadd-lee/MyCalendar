package com.mycalendar.model.dto;

import com.mycalendar.model.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    String id;
    String name;
    @Email
    String email;
    String created_date;
    String updated_date;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.created_date = user.getCreated_date();
        this.updated_date = user.getUpdated_date();
    }
}
