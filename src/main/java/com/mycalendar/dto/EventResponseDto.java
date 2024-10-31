package com.mycalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class EventResponseDto {
    Integer id;
    String userName;
    String created_date;
    String updated_date;
}
