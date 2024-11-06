package com.mycalendar.service;

import com.mycalendar.model.Event;
import com.mycalendar.model.User;

import java.util.List;

public interface EventService {
    Event createEvent(Event event, User user);


    Event findEventById(String id);

    List<Event> findAllEventByDate(Event event,User user);

    Event updateEvent(Event event,User user);

    void deleteEvent(Event event);

    List<Event> findEventsWithPaging(int pageNum, int size);
}
