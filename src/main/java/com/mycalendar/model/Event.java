package com.mycalendar.model;

import com.mycalendar.model.dto.EventRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Event {
    @Setter
    String id;
    @Setter
    String userId;
    String password;
    String title;
    String created_date;
    String updated_date;

    public Event(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public Event(EventRequestDto dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.title = dto.getTitle();
        this.updated_date = dto.getUpdated_date();
    }
}
