package com.mycalendar.repository;


import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import com.mycalendar.model.dto.EventRequestDto;

import java.util.List;

public interface EventRepository {
    Event createEvent(EventRequestDto dto, User user);

    List<Event> findAllEventByDate(EventRequestDto eventRequestDto);

    Event findEventById(Integer id);

    int updateEvent(Integer id, EventRequestDto dto);

    int deleteEvent(Integer id,EventRequestDto dto);

    List<Event> findEventsWithPaging(int pageNum, int size);
}
