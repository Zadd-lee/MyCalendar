package com.mycalendar.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    Integer id;
    String name;
    String email;
    String created_date;
    String updated_date;
}
