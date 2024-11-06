package com.mycalendar.repository.imp;

import com.mycalendar.common.constants.UserErrorCode;
import com.mycalendar.common.exception.CustomException;
import com.mycalendar.model.User;
import com.mycalendar.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImp(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findUserById(String id) {
        List<User> result = jdbcTemplate.query("select id,name from users where id = ?", userRowMapper(), id);

        return result.stream().findAny().orElseThrow(()->new CustomException(UserErrorCode.NOT_FOUND));
    }

    @Override
    public User findUserByName(String name) {
        List<User> result = jdbcTemplate.query("select id,name from users where name =?",
                userRowMapper(), name);

        return result.stream().findAny().orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }

    @Override
    public int updateUserName(User user) {
        int updatedRows = jdbcTemplate.update("update users set name = ? where id = ?;", user.getName(), user.getId());
        return updatedRows;

    }

    @Override
    public User findUserByEventId(String id) {
        List<User> result = jdbcTemplate.query("select u.id,u.name from users u inner join events e on u.id=e.user_id where e.id =?",
                userRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getString("id"),
                        rs.getString("name")
                );
            }
        };
    }

}
