package com.mycalendar.service;

import com.mycalendar.model.User;

public interface UserService {
    public User findUserById(String id);

    public User findUserByName(String name);

    User findUserByEventId(String id);
}
