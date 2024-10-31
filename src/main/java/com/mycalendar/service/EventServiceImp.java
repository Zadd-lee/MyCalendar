package com.mycalendar.service;

import com.mycalendar.dto.EventRequestDto;
import com.mycalendar.dto.EventResponseDto;
import com.mycalendar.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService{
    private final EventRepository repository;


    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {

        //유효성 검사
        if (dto.getName() == null || dto.getPassword() == null
                || dto.getTitle() == null||dto.getUpdated_date()!=null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return repository.createEvent(dto);
    }

    @Override
    public EventResponseDto findEventById(Integer id) {
        EventResponseDto result = repository.findEventById(id);
        //값이 없을 경우 404
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Override
    public List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto)   {
        if(eventRequestDto.getPassword()!=null||eventRequestDto.getTitle()!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<EventResponseDto> allEventByDate = repository.findAllEventByDate(eventRequestDto);
        if (allEventByDate.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return allEventByDate;
    }

    @Override
    public EventResponseDto updateEvent(Integer id, EventRequestDto dto) {

        if (dto.getName() == null || dto.getTitle() == null
                || dto.getPassword() == null || dto.getUpdated_date() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int i = repository.updateEvent(id, dto);

        if (i == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return repository.findEventById(id);
    }

    @Override
    public void deleteEvent(Integer id, EventRequestDto eventRequestDto) {
        if (eventRequestDto.getPassword() == null || eventRequestDto.getName() != null
                || eventRequestDto.getTitle() != null||eventRequestDto.getUpdated_date()!=null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int row = repository.deleteEvent(id,eventRequestDto);
        if (row == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
