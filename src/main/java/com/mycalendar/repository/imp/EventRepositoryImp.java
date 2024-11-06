package com.mycalendar.repository.imp;

import com.mycalendar.common.constants.EventErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.Event;
import com.mycalendar.repository.EventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public int createEvent(Event event) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("events").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId",event.getUserId());
        parameters.put("password", event.getPassword());
        parameters.put("title", event.getTitle());
        parameters.put("created_date", LocalDateTime.now());
        parameters.put("updated_date", LocalDateTime.now());
        parameters.put("delyn", "N");

        Number key = insert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.intValue();
    }


    @Override
    public Event findEventById(String id) {
        List<Event> result = jdbcTemplate.query("select * from events where delyn = 'N' and id = ?", eventRowMapper(), id);
        return result.stream()
                .findAny().orElseThrow(() -> new CustomException(EventErrorCode.NOT_FOUND));
    }

    @Override
    public List<Event> findAllEventByDate(Event event) {
        StringBuffer query = new StringBuffer();
        List<String> queryArgs = new ArrayList<>();

        query.append("select * from events where 1=1 AND delyn = 'N' ");

        if (event.getUserId() != null && event.getUpdated_date() != null) {
            query.append("AND DATE_FORMAT(UPDATED_DATE,'%Y-%m-%d') = ?  AND user_id = ?");
            queryArgs.add(0,event.getUpdated_date());
            queryArgs.add(1,event.getUserId());
        } else if (event.getUpdated_date() != null) {
            query.append("AND DATE_FORMAT(e.UPDATED_DATE,'%Y-%m-%d') = ?");
            queryArgs.add(0,event.getUpdated_date());
        } else if (event.getUserId()!=null) {
            query.append("AND user_id = ?");
            queryArgs.add(0,event.getUserId());
        }


        /* Lastly, execute the query */
        return this.jdbcTemplate.query(query.toString(),
                eventRowMapper(),queryArgs.toArray());

    }


    @Override
    public List<Event> findEventsWithPaging(int pageNum, int size) {
        List<Event> result = jdbcTemplate.query("select * from events where delyn = 'N' limit ? offset ?", eventRowMapper(), size, pageNum * size);
        return result;
    }

    @Override
    public int updateEvent(Event event) {
        int updatedRows = jdbcTemplate.update("update events set TITLE = ?, UPDATED_DATE=now() where id = ? and PASSWORD = ?", event.getTitle(),event.getId(),event.getPassword());

        return updatedRows;
    }

    @Override
    public int deleteEvent(Event event) {
        return jdbcTemplate.update("update events  set delyn='Y'  where id=?;", event.getId());
    }

    private RowMapper<String> userIdRowMapper() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("id");
            }
        };
    }

    private RowMapper<Event> eventRowMapper() {
        return new RowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Event(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("title"),
                         rs.getString("created_date"),
                         rs.getString("updated_date")
                );
            }
        };
    }
}

