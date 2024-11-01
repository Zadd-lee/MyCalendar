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
@RequestMapping("/events")
public class EventController {
    //todo 이 컨트롤러에 userid 를 가져오는 객체를 400으로 리턴하게 필터링

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
