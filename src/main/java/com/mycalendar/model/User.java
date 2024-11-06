package com.mycalendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    String id;
    String name;
    String email;
    String created_date;
    String updated_date;

}
