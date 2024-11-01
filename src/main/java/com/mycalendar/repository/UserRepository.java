package com.mycalendar.repository;

import com.mycalendar.dto.UserResponseDto;

public interface UserRepository {
    UserResponseDto findUserById(Integer id);
}
