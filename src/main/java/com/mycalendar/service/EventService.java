package com.mycalendar.service;

import com.mycalendar.model.dto.EventRequestDto;
import com.mycalendar.model.dto.EventResponseDto;

import java.util.List;

public interface EventService {
    EventResponseDto createEvent(EventRequestDto dto);


    EventResponseDto findEventById(Integer id);

    List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto);

    EventResponseDto updateEvent(Integer id, EventRequestDto eventRequestDto);

    void deleteEvent(Integer id, EventRequestDto eventRequestDto);
}
