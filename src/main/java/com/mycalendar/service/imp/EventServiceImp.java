package com.mycalendar.service.imp;

import com.mycalendar.common.constants.EventErrorCode;
import com.mycalendar.common.constants.UserErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.Event;
import com.mycalendar.model.User;
import com.mycalendar.repository.EventRepository;
import com.mycalendar.repository.UserRepository;
import com.mycalendar.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

//todo username 기준 으로 들어왔을때 user 값 정의내려주기!!
    @Override
    public Event createEvent(Event event, User user) {

        //user 검증
        validateUserParameter(user);
        //event 검증
        if (event.getId() != null || event.getPassword() == null || event.getTitle() == null) {
            throw new CustomException(EventErrorCode.INVALID_FIELD);
        }

        //todo 일정 생성 결과 key 값
        int createdEventId = eventRepository.createEvent(event);

        //일정 조회
        Event eventById = eventRepository.findEventById(String.valueOf(createdEventId));

        //validate
        validateNull(eventById);

        return eventById;
    }

    @Override
    public Event findEventById(String id) {
        Event eventById = eventRepository.findEventById(id);

        //validate
        validateNull(eventById);

        return eventById;

    }

    @Override
    public List<Event> findAllEventByDate(Event event, User user) {


        List<Event> allEventByDate = eventRepository.findAllEventByDate(event);

        //validate
        validateNull(allEventByDate);

        return allEventByDate;
    }

    @Transactional
    @Override
    public Event updateEvent(Event event, User user) {
        //password validation
        validatePassword(event);

        //사용자 이름 변경
        int rows = userRepository.updateUserName(user);


        //일정 변경
        eventRepository.updateEvent(event);

        //값 찾기
        return findEventById(event.getId());
    }

    @Override
    public void deleteEvent(Event event) {
        validatePassword(event);
        eventRepository.deleteEvent(event);
    }

    private void validatePassword(Event event) {
        Event eventBeforeUpdate = findEventById(event.getId());

        //일정

        validateNull(eventBeforeUpdate);

        if (!eventBeforeUpdate.getPassword().equals(event.getPassword())) {
            throw new CustomException(EventErrorCode.PASSWORD_INCORRECT);
        }
    }

    @Override
    public List<Event> findEventsWithPaging(int pageNum, int size) {
        List<Event> result = eventRepository.findEventsWithPaging(pageNum, size);


        return result;
    }

    private void validateUserParameter(User user) {
        if (user.getName() == null && user.getId() == null) {
            throw new CustomException(UserErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     *결과값의 유무를 검사
     *값이 없으면 EventErrorCode.NOT_FOUNT 리턴
     * @param result
     */
    private static void validateNull(List<Event> result) {
        if (result.size() == 0) {
            throw new CustomException(EventErrorCode.NOT_FOUND);
        }
    }

    private void validateNull(Event result) {
        if (result == null) {
            throw new CustomException(EventErrorCode.NOT_FOUND);
        }
    }
}
