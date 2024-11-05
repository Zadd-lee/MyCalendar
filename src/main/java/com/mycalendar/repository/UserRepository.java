package com.mycalendar.repository;

import com.mycalendar.model.dto.UserResponseDto;

public interface UserRepository {
    UserResponseDto findUserById(Integer id);
}
