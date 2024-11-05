package com.mycalendar.repository;

import com.mycalendar.model.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImp(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto findUserById(Integer id) {
        List<UserResponseDto> result = jdbcTemplate.query("select * from users where id = ?", userRowMapper(), id);

        return result.stream().findAny().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private RowMapper<UserResponseDto> userRowMapper() {
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("created_date"),
                        rs.getString("updated_date")

                );
            }
        };
    }

}
