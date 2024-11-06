package com.mycalendar.repository;

import com.mycalendar.model.User;

public interface UserRepository {
    User findUserById(Integer id);
}
