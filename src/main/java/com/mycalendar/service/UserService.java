package com.mycalendar.service;

import com.mycalendar.model.dto.UserResponseDto;

public interface UserService {
    public UserResponseDto findUserById(Integer id);
}
