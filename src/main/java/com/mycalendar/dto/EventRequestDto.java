package com.mycalendar.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventRequestDto {
    String name;
    String password;
    String title;
    String updated_date;
}
