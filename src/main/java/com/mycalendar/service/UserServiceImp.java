package com.mycalendar.service;

import com.mycalendar.model.User;
import com.mycalendar.model.dto.UserResponseDto;
import com.mycalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository repository;

    @Override
    public UserResponseDto findUserById(String id) {

        User user = repository.findUserById(id);
        return new UserResponseDto(user);
    }

}
