package com.mycalendar.model.dto;

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

}
