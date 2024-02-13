package com.example.demo.rep.Implementation;

import com.example.demo.models.Employee;
import com.example.demo.rep.EmployeeRepos;
import com.example.demo.utils.Queries;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.constants.Constants.SqlParams.AGE;
import static com.example.demo.constants.Constants.SqlParams.CITY;
import static com.example.demo.constants.Constants.SqlParams.CURRENT_PAGE;
import static com.example.demo.constants.Constants.SqlParams.DATE;
import static com.example.demo.constants.Constants.SqlParams.EMPLOYEE_ACTIVE;
import static com.example.demo.constants.Constants.SqlParams.EMPLOYEE_DEPARTMENT_ID;
import static com.example.demo.constants.Constants.SqlParams.EMPLOYEE_ID;
import static com.example.demo.constants.Constants.SqlParams.EMPLOYEE_NAME;
import static com.example.demo.constants.Constants.SqlParams.PAGE_SIZE;

@Repository
@RequiredArgsConstructor
public class EmployeeReposImpl implements EmployeeRepos {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Employee> getAllEmployee() {

        return namedParameterJdbcTemplate.query(Queries.Employees.GET_ALL.sql(), new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public List<Employee> getAllEmployeesPagination(int pageSize, int currentPage) {

        int offset = (currentPage - 1) * pageSize;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(PAGE_SIZE, pageSize)
                .addValue(CURRENT_PAGE, offset);

        return namedParameterJdbcTemplate.query(Queries.Employees.GET_ALL_PAGINATION.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public void saveEmployee(Employee employee) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmpID())
                .addValue(EMPLOYEE_NAME, employee.getEmpName())
                .addValue(EMPLOYEE_ACTIVE, employee.getEmpActive())
                .addValue(EMPLOYEE_DEPARTMENT_ID, employee.getDepId())
                .addValue(DATE, employee.getDate())
                .addValue(AGE, employee.getAge())
                .addValue(CITY, employee.getCity());

        namedParameterJdbcTemplate.update(Queries.Employees.INSERT.sql(), mapSqlParameterSource);
    }

    @Override
    public void deleteEmployeeById(int id) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, id);

        namedParameterJdbcTemplate.update(Queries.Employees.DELETE.sql(), mapSqlParameterSource);
    }

    @Override
    public void editEmployee(Employee employee) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmpID())
                .addValue(EMPLOYEE_NAME, employee.getEmpName())
                .addValue(EMPLOYEE_DEPARTMENT_ID, employee.getDepId())
                .addValue(DATE, employee.getDate())
                .addValue(AGE, employee.getAge())
                .addValue(CITY, employee.getCity())
                .addValue(EMPLOYEE_ACTIVE, employee.getEmpActive());

        namedParameterJdbcTemplate.update(Queries.Employees.UPDATE.sql(), mapSqlParameterSource);
    }

    @Override
    public Employee getEmployeeById(int id) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, id);

        return namedParameterJdbcTemplate.queryForObject(Queries.Employees.GET_BY_ID.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public List<Employee> getEmployeeByName(String name, int pageSize, int currentPage) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_NAME, "%" + name + "%");

        return namedParameterJdbcTemplate.query(Queries.Employees.GET_BY_NAME.sql(), mapSqlParameterSource, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public Long totalCount() {

        return jdbcTemplate.queryForObject(Queries.Employees.TOTAL_COUNT.sql(), Long.class);
    }
}
