package com.mycalendar.model.dto;

import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import lombok.Getter;

@Getter
public class EventResponseDto {

    String id;
    String userId;
    String title;
    String created_date;
    String updated_date;

    String userName;

    public EventResponseDto(Event event, User user) {
        this.id = event.getId();
        this.userId = user.getId();
        this.title = event.getTitle();
        this.created_date = event.getCreated_date();
        this.updated_date = event.getUpdated_date();
        this.userName = user.getName();
    }

}
