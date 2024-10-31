package com.mycalendar.model;

import lombok.Getter;

@Getter
public class Event {
    Integer id;
    Integer useId;
    String password;
    String title;
    String created_date;
    String updated_date;
}
