package com.mycalendar.dto;

import lombok.Getter;

@Getter
public class EventRequestDto {
    int userId;
    String name;
    String password;
    String title;
    String updated_date;
}
