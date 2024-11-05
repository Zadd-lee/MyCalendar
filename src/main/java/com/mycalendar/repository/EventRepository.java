package com.mycalendar.repository;


import com.mycalendar.model.dto.EventRequestDto;
import com.mycalendar.model.dto.EventResponseDto;

import java.util.List;

public interface EventRepository {
    EventResponseDto createEvent(EventRequestDto dto);

    List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto);

    EventResponseDto findEventById(Integer id);

    int updateEvent(Integer id, EventRequestDto dto);

    int deleteEvent(Integer id,EventRequestDto dto);

    List<EventResponseDto> findEventsWithPaging(int pageNum, int size);
}
