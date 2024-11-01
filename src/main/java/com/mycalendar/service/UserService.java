package com.mycalendar.service;

import com.mycalendar.dto.UserResponseDto;

public interface UserService {
    public UserResponseDto findUserById(Integer id);
}
