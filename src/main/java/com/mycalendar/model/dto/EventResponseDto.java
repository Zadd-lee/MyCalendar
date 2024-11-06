package com.mycalendar.model.dto;

import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponseDto {
    Integer id;
    String userName;
    String userId;
    String created_date;
    String updated_date;


    public EventResponseDto(Event event, User user) {
        this.id = event.getId();
        this.userName = user.getName();
        this.userId = user.getId();
        this.created_date = event.getCreated_date();
        this.updated_date = event.getUpdated_date();
    }
}
