package com.mycalendar.repository;


import com.mycalendar.model.Event;

import java.util.List;

public interface EventRepository {
    int createEvent(Event event);

    List<Event> findAllEventByDate(Event event);

    Event findEventById(String id);

    int updateEvent(Event event);

    int deleteEvent(Event event);

    List<Event> findEventsWithPaging(int pageNum, int size);
}
