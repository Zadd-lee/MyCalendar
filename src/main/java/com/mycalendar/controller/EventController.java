package com.mycalendar.controller;

import com.mycalendar.common.constants.UserErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import com.mycalendar.model.dto.EventRequestDto;
import com.mycalendar.model.dto.EventResponseDto;
import com.mycalendar.service.EventService;
import com.mycalendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Validated @RequestBody EventRequestDto eventRequestDto) {
        log.info("createEvent");

        Map<String, Object> domainList = convertDtoToDomain(eventRequestDto);
        Event event = (Event) domainList.get("event");
        User user = (User) domainList.get("user");

        Event createdEvent = eventService.createEvent(event,user);

        EventResponseDto responseDto = new EventResponseDto(createdEvent, user);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> findAllEventsByDate(@RequestBody EventRequestDto eventRequestDto) {
        log.info("findAllEventsByDate");
        Map<String, Object> domainList = convertDtoToDomain(eventRequestDto);

        List<Event> eventList = eventService.findAllEventByDate((Event) domainList.get("event"),(User)domainList.get("user"));

        List<EventResponseDto> responseDtos = eventList.stream()
                .map(x -> new EventResponseDto(x, (User) domainList.get("user")))
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> findEventById(@PathVariable(value = "id") String id) {
        log.info("findEventById");

        Map<String, Object> domainList = convertDtoToDomain(id);

        User user = (User) domainList.get("user");

        Event event = eventService.findEventById(id);

        EventResponseDto responseDto = new EventResponseDto(event, user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping({"page", "page?pageNum={pageNum}&size={size}"})
    public ResponseEntity<List<EventResponseDto>> findEventsWithPaging(
            @RequestParam(value = "pageNum") int pageNum,
            @RequestParam(value = "size") int size
    ) {
        List<Event> eventList= eventService.findEventsWithPaging(pageNum, size);

        List<EventResponseDto> dto = eventList.stream()
                .map(x -> new EventResponseDto(x, userService.findUserById(x.getUserId())))
                .toList();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable(value = "id") String id,
                                                        @Validated @RequestBody EventRequestDto eventRequestDto) {
        log.info("update event");
        Map<String, Object> domainList = convertDtoToDomain(eventRequestDto,id);
        Event event = eventService.updateEvent((Event) domainList.get("event"), (User) domainList.get("user"));
        EventResponseDto responseDto = new EventResponseDto(event,(User) domainList.get("user"));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(value = "id") String id,
                                            @RequestBody EventRequestDto eventRequestDto) {
        log.info("delete event");
        Event event = new Event(eventRequestDto);
        event.setId(id);
        eventService.deleteEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, Object> convertDtoToDomain(String id) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.findUserByEventId(id);

        result.put("user", user);

        return result;
    }

    private Map<String, Object> convertDtoToDomain(EventRequestDto eventRequestDto, String id) {
        Map<String, Object> result = new HashMap<>();

        User user = userService.findUserByEventId(id);
        user.setName(eventRequestDto.getName());

        Event event = createEventDomain(eventRequestDto, user);
        event.setId(id);

        result.put("user", user);
        result.put("event", event);
        return result;
    }

    private  Map<String,Object> convertDtoToDomain(EventRequestDto eventRequestDto) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", createUserDomainWithSearch(eventRequestDto));
        result.put("event",createEventDomain(eventRequestDto, (User) result.get("user")));
        return result;
    }

    private Event createEventDomain(EventRequestDto eventRequestDto,User user) {
        Event event = new Event(eventRequestDto);
        event.setUserId(user.getId());
        return event;
    }

    private User createUserDomainWithSearch(EventRequestDto eventRequestDto) {
        User user = new User();
        if (eventRequestDto.getUserId() == null && eventRequestDto.getName() != null) {
            user = userService.findUserByName(eventRequestDto.getName());
        } else if (eventRequestDto.getUserId() != null && eventRequestDto.getName() == null) {
            user = userService.findUserById(eventRequestDto.getUserId());
        }else{
            throw new CustomException(UserErrorCode.INVALID_PARAMETER);
        }
        return user;
    }
}
