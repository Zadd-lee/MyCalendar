package com.mycalendar.service;

import com.mycalendar.common.constants.EventErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.dto.EventRequestDto;
import com.mycalendar.model.dto.EventResponseDto;
import com.mycalendar.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService{
    private final EventRepository repository;


    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {

        //유효성 검사 - 작성자 이름 기준
        if (dto.getName() == null || dto.getPassword() == null
                || dto.getTitle() == null||dto.getUpdated_date()!=null) {
            throw new CustomException(EventErrorCode.NOT_FOUND);
        }
        //유효성 검사 - 작성자 ID 기준

        return repository.createEvent(dto);
    }

    @Override
    public EventResponseDto findEventById(Integer id) {
        EventResponseDto result = repository.findEventById(id);
        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(result);
        return result;
    }

    @Override
    public List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto)   {
        if(eventRequestDto.getPassword()!=null||eventRequestDto.getTitle()!=null){
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }
        List<EventResponseDto> allEventByDate = repository.findAllEventByDate(eventRequestDto);


        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(allEventByDate.size());

        return allEventByDate;
    }

    @Override
    public EventResponseDto updateEvent(Integer id, EventRequestDto dto) {

        if (dto.getName() == null || dto.getTitle() == null
                || dto.getPassword() == null || dto.getUpdated_date() != null) {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }

        int i = repository.updateEvent(id, dto);

        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(i);
        return repository.findEventById(id);
    }

    @Override
    public void deleteEvent(Integer id, EventRequestDto eventRequestDto) {
        if (eventRequestDto.getPassword() == null || eventRequestDto.getName() != null
                || eventRequestDto.getTitle() != null||eventRequestDto.getUpdated_date()!=null) {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }
        int row = repository.deleteEvent(id,eventRequestDto);


        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(row);

    }

    @Override
    public List<EventResponseDto> findEventsWithPaging(int pageNum, int size) {
        List<EventResponseDto> result = repository.findEventsWithPaging(pageNum, size);

        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(result.size());


        return result;
    }

    private static void validateResult(int row) {
        if (row == 0) {
            throw new CustomException(EventErrorCode.NOT_FOUND);
        }
    }

    private static void validateResult(EventResponseDto result) {
        if (result == null) {
            throw new CustomException(EventErrorCode.NOT_FOUND);
        }
    }
}
