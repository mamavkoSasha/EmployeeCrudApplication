package com.example.demo.rep.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.UserDto;
import com.example.demo.rep.UserRepos;
import com.example.demo.utils.Queries;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.example.demo.constants.Constants.Messages.FAILED_REGISTRATION;
import static com.example.demo.constants.Constants.SqlParams.EMAIL;
import static com.example.demo.constants.Constants.SqlParams.PASSWORD;
import static com.example.demo.constants.Constants.SqlParams.USERNAME;
import static com.example.demo.constants.Constants.SqlParams.USER_ID;

@Repository
@RequiredArgsConstructor
public class UserReposImpl implements UserRepos {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public String registerUser(String username, String email, String encodedPassword) {

        var userId = UUID.randomUUID().toString();

        var mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(USER_ID, userId)
                .addValue(USERNAME, username)
                .addValue(PASSWORD, encodedPassword)
                .addValue(EMAIL, email);

        var insertedRows = namedParameterJdbcTemplate.update(Queries.Users.INSERT.sql(), mapSqlParameterSource);

        Optional.of(insertedRows)
                .filter(value -> insertedRows > 0)
                .orElseThrow(() -> new RestException(FAILED_REGISTRATION, HttpStatus.INTERNAL_SERVER_ERROR));

        return userId;
    }

    @Override
    public Optional<UserDto> gerUserByUsername(String username) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(USERNAME, username);

        return namedParameterJdbcTemplate.query(Queries.Users.SELECT_BY_USERNAME.sql(), mapSqlParameterSource,
                        new BeanPropertyRowMapper<>(UserDto.class)).stream()
                .findFirst();
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMAIL, email);

        return namedParameterJdbcTemplate.query(Queries.Users.SELECT_BY_EMAIL.sql(), mapSqlParameterSource,
                        new BeanPropertyRowMapper<>(UserDto.class)).stream()
                .findFirst();
    }

    @Override
    public Optional<UserDto> getUserById(String userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(USER_ID, userId);

        return namedParameterJdbcTemplate.query(Queries.Users.SELECT_BY_USER_ID.sql(), mapSqlParameterSource,
                        new BeanPropertyRowMapper<>(UserDto.class)).stream()
                .findFirst();
    }

    @Override
    public void updateUser(UserDto userDto, String userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(USERNAME, userDto.getUsername())
                .addValue(PASSWORD, userDto.getPassword())
                .addValue(EMAIL, userDto.getEmail())
                .addValue(USER_ID, userId);

        namedParameterJdbcTemplate.update(Queries.Users.UPDATE.sql(), mapSqlParameterSource);

    }

    @Override
    public void deleteUser(String userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(USER_ID, userId);

        namedParameterJdbcTemplate.update(Queries.Users.DELETE.sql(), mapSqlParameterSource);

    }
}
