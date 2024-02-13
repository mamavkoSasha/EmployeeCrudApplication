package com.example.demo.rep.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.rep.DepartmentRepos;
import com.example.demo.utils.Queries;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.constants.Constants.SqlParams.CURRENT_PAGE;
import static com.example.demo.constants.Constants.SqlParams.DEPARTMENT_ID;
import static com.example.demo.constants.Constants.SqlParams.DEPARTMENT_NAME;
import static com.example.demo.constants.Constants.SqlParams.PAGE_SIZE;

@Repository
@RequiredArgsConstructor
public class DepartmentReposImpl implements DepartmentRepos {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Department> getAllDepartments() {

        return namedParameterJdbcTemplate.query(Queries.Department.GET_ALL.sql(), new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public List<Department> getAllDepartmentsPagination(int pageSize, int currentPage) {

        int offset = (currentPage - 1) * pageSize;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(PAGE_SIZE, pageSize)
                .addValue(CURRENT_PAGE, offset);

        return namedParameterJdbcTemplate.query(Queries.Department.GET_ALL_PAGINATION.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Department.class));
    }


    @Override
    public void saveDepartment(Department department) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, department.getDpID())
                .addValue(DEPARTMENT_NAME, department.getDpName());

        namedParameterJdbcTemplate.update(Queries.Department.INSERT.sql(), mapSqlParameterSource);
    }

    @Override
    public void deleteDepartmentById(int id) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, id);

        namedParameterJdbcTemplate.update(Queries.Department.DELETE.sql(), mapSqlParameterSource);
    }

    @Override
    public void editDepartment(Department department) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_NAME, department.getDpName())
                .addValue(DEPARTMENT_ID, department.getDpID());

        namedParameterJdbcTemplate.update(Queries.Department.UPDATE.sql(), mapSqlParameterSource);
    }

    @Override
    public Department getDepartmentById(int id) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, id);
        return namedParameterJdbcTemplate.queryForObject(Queries.Department.GET_BY_ID.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public Integer countByName(String dpName) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_NAME, dpName);

        return namedParameterJdbcTemplate.queryForList(Queries.Department.COUNT_BY_NAME.sql(), mapSqlParameterSource, Integer.class).stream()
                .findFirst()
                .orElseThrow(() -> new RestException("", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public List<Department> getDepartmentByName(String name, int pageSize, int currentPage) {

        int offset = (currentPage - 1) * pageSize;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_NAME, "%" + name + "%")
                .addValue(PAGE_SIZE, pageSize)
                .addValue(CURRENT_PAGE, offset);

        return namedParameterJdbcTemplate.query(Queries.Department.GET_BY_NAME.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public Long totalCount() {

        return jdbcTemplate.queryForObject(Queries.Department.TOTAL_COUNT.sql(), Long.class);
    }

}

