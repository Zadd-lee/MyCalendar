package com.mycalendar.repository;


import com.mycalendar.dto.EventRequestDto;
import com.mycalendar.dto.EventResponseDto;

import java.util.List;

public interface EventRepository {
    EventResponseDto createEvent(EventRequestDto dto);

    List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto);

    EventResponseDto findEventById(Integer id);

    int updateEvent(Integer id, EventRequestDto dto);

    int deleteEvent(Integer id,EventRequestDto dto);
}
