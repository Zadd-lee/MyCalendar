package com.mycalendar.service;

import com.mycalendar.common.constants.EventErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import com.mycalendar.model.dto.EventRequestDto;
import com.mycalendar.model.dto.EventResponseDto;
import com.mycalendar.repository.EventRepository;
import com.mycalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService{
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {
        User user;
        //유효성 검사 - 유저가 있는지 확인
        if (dto.getName() == null && dto.getUserId() != null) {
            user = userRepository.findUserById(dto.getUserId());
        } else if (dto.getName() != null && dto.getUserId() == null) {
            user = userRepository.findUserByName(dto.getName());
        } else {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }
        //유효성 검사 - dto 의 필드 값 검사
        if (dto.getPassword() == null || dto.getTitle() == null || dto.getUpdated_date() != null) {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }

        //user + dto 값 기준 생성
        Event event = eventRepository.createEvent(dto, user);


        //생성된 일정으로 dto 변환 후 리턴
        return new EventResponseDto(event,user);
    }

    @Override
    public EventResponseDto findEventById(Integer id) {
        EventResponseDto result = eventRepository.findEventById(id);
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
        List<EventResponseDto> allEventByDate = eventRepository.findAllEventByDate(eventRequestDto);


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

        int i = eventRepository.updateEvent(id, dto);

        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(i);
        return eventRepository.findEventById(id);
    }

    @Override
    public void deleteEvent(Integer id, EventRequestDto eventRequestDto) {
        if (eventRequestDto.getPassword() == null || eventRequestDto.getName() != null
                || eventRequestDto.getTitle() != null||eventRequestDto.getUpdated_date()!=null) {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }
        int row = eventRepository.deleteEvent(id,eventRequestDto);


        //결과값의 유무를 검사
        //값이 없으면 EventErrorCode.NOT_FOUNT 리턴
        validateResult(row);

    }

    @Override
    public List<EventResponseDto> findEventsWithPaging(int pageNum, int size) {
        List<EventResponseDto> result = eventRepository.findEventsWithPaging(pageNum, size);

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
