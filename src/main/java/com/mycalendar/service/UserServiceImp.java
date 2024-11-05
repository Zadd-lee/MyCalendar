package com.mycalendar.service;

import com.mycalendar.model.dto.UserResponseDto;
import com.mycalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository repository;

    @Override
    public UserResponseDto findUserById(Integer id) {
        return repository.findUserById(id);
    }
}
