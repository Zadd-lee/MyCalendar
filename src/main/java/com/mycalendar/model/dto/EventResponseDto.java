package com.mycalendar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    Integer id;
    String userName;
    String created_date;
    String updated_date;
}
