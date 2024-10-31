package com.mycalendar.repository;

import com.mycalendar.dto.EventRequestDto;
import com.mycalendar.dto.EventResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventRepositoryImp implements EventRepository {
    private final JdbcTemplate jdbcTemplate;

    public EventRepositoryImp(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public EventResponseDto createEvent(EventRequestDto dto) {
        //name 값 기준으로 user id 값 가져오기
        List<String> selectIdFromUsers = jdbcTemplate.query("select id from users", userIdRowMapper());
        if (selectIdFromUsers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String userId = selectIdFromUsers.get(0);


        //일정 생성
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("events").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId",userId);
        parameters.put("password", dto.getPassword());
        parameters.put("title", dto.getTitle());
        parameters.put("created_date", LocalDateTime.now());
        parameters.put("updated_date", LocalDateTime.now());

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findEventById(key.intValue());
    }


    @Override
    public List<EventResponseDto> findAllEventByDate(EventRequestDto eventRequestDto) {
        //todo mybatis 처럼 동적쿼리 생성
       return jdbcTemplate.query("select e.id,u.NAME as username,e.CREATED_DATE,e.UPDATED_DATE " +
               "from events e inner join USERS U " +
               "on e.USER_ID = U.ID " +
               "where DATE_FORMAT(e.UPDATED_DATE,'%Y-%m-%d') = ?  or USER_ID = ?", eventRowMapper(),eventRequestDto.getUpdated_date(), eventRequestDto.getName());
    }

    @Override
    public EventResponseDto findEventById(Integer id) {
        List<EventResponseDto> result = jdbcTemplate.query("select e.id,u.NAME as username, e.CREATED_DATE,e.UPDATED_DATE " +
                "from users u inner join EVENTS E on u.ID = E.USER_ID " +
                "where e.id = ?", eventRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Transactional
    @Override
    public int updateEvent(Integer id, EventRequestDto dto) {
        int updateRowName = jdbcTemplate.update("update users " +
                "set name = ? " +
                "where id=(select USER_ID " +
                "from events " +
                "where id = ? and password = ?)", dto.getName(), id, dto.getPassword());
        int updatedRowsEvent = jdbcTemplate.update("update events set TITLE = ?, UPDATED_DATE=now() where id = ? and PASSWORD = ?", dto.getTitle(), id, dto.getPassword());
        if (updateRowName == 0 || updatedRowsEvent==0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return findEventById(id).getId();
    }

    @Override
    public int deleteEvent(Integer id,EventRequestDto requestDto) {
        return jdbcTemplate.update("delete from events where id=? and password=?;", id,requestDto.getPassword());
    }

    private RowMapper<String> userIdRowMapper() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("id");
            }
        };
    }

    private RowMapper<EventResponseDto> eventRowMapper() {
        return new RowMapper<EventResponseDto>() {
            @Override
            public EventResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EventResponseDto(
                        rs.getInt("id"),
                        rs.getString("username"),
                         rs.getString("created_date"),
                         rs.getString("updated_date")
                );
            }
        };
    }
}

