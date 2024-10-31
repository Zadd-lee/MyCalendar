package com.mycalendar.controller;

import com.mycalendar.dto.EventRequestDto;
import com.mycalendar.dto.EventResponseDto;
import com.mycalendar.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar/events")
public class EventController {

    private final EventService service;

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        log.info("createEvent");
        EventResponseDto event = service.createEvent(eventRequestDto);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> findAllEventsByDate(@RequestBody EventRequestDto eventRequestDto) {
        log.info("findAllEventsByDate");
        List<EventResponseDto> dto = service.findAllEventByDate(eventRequestDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> findEventById(@PathVariable(value = "id") Integer id) {
        log.info("findEventById");
        //todo 여기서 id값이 잘못 들어왔을 경우, 400 코드 리턴이 필요한데, 이럴 경우 어떻게 해야할지
        EventResponseDto event = service.findEventById(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable(value = "id") Integer id,
                                                        @RequestBody EventRequestDto eventRequestDto) {
        log.info("update event");
        EventResponseDto event = service.updateEvent(id, eventRequestDto);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(value = "id") Integer id,
                                            @RequestBody EventRequestDto eventRequestDto) {
        log.info("delete event");
        service.deleteEvent(id, eventRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
