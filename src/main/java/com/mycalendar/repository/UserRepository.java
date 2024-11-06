package com.mycalendar.repository;

import com.mycalendar.model.User;

public interface UserRepository {
    User findUserById(String id);

    User findUserByName(String name);

    int updateUserName(User user);

    User findUserByEventId(String id);
}
