package com.mycalendar.service.imp;

import com.mycalendar.common.constants.UserErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.User;
import com.mycalendar.repository.UserRepository;
import com.mycalendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository repository;

    @Override
    public User findUserById(String id) {

        User user = repository.findUserById(id);
        validateFindResult(user);
        return user;
    }

    @Override
    public User findUserByName(String name) {
        User userByName = repository.findUserByName(name);
        validateFindResult(userByName);
        return userByName;
    }

    @Override
    public User findUserByEventId(String id) {
        User user = repository.findUserByEventId(id);
        validateFindResult(user);
        return user;
    }

    private static void validateFindResult(User user) {
        if (user == null) {
            throw new CustomException(UserErrorCode.NOT_FOUND);
        }
    }

}
