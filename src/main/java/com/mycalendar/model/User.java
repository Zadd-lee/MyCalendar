package com.mycalendar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class User {
    @Setter
    String id;
    @Setter
    String name;
    String email;
    String created_date;
    String updated_date;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
