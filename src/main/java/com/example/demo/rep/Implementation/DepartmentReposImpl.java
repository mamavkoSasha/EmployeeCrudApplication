package com.example.demo.rep.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.Employee;
import com.example.demo.rep.DepartmentRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentReposImpl implements DepartmentRepos {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentReposImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> getAllDepartments() {
        String query = "SELECT * FROM departments p";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public void saveDepartment(Department department) {
        String query = "INSERT INTO departments (dpID, dpName) VALUE(:dpID, :dpName)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("dpID", department.getDpID())
                .addValue("dpName", department.getDpName());

        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteDepartmentById(int id) {
        String query = "DELETE FROM departments WHERE dpID = :dpID";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("dpID", id);
        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void editDepartment(Department department) {
        String query = "UPDATE departments " +
                "SET dpName = :dpName "+
                "WHERE dpID = :dpID";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("dpName", department.getDpName())
                .addValue("dpID", department.getDpID());

        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public Department getDepartmentById(int id) {

        String query = "SELECT p.dpID AS dpID,\n" +
                "       p.dpName AS dpName \n" +
                "FROM departments p \n" +
                "WHERE p.dpID = :dpID";

        MapSqlParameterSource mapSqlParameterSource =new MapSqlParameterSource()
                .addValue("dpID", id);
        return namedParameterJdbcTemplate.queryForObject(query,mapSqlParameterSource ,new BeanPropertyRowMapper<>(Department.class));
    }

    @Override
    public Integer countByName(String dpName) {
        String query = "SELECT COUNT(d.dpID) " +
                "FROM departments d " +
                "WHERE d.dpName = :dpName";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("dpName", dpName);

        return namedParameterJdbcTemplate.queryForList(query, parameterSource, Integer.class).stream()
                .findFirst()
                .orElseThrow(() -> new RestException("", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public List<Department> getDepartmentByName(String name) {
        String query = "SELECT d.dpID AS dpID,\n" +
                "       d.dpName AS dpName \n" +
                "FROM departments d \n"+
                "WHERE dpName LIKE :dpName";

        MapSqlParameterSource mapSqlParameterSource =new MapSqlParameterSource()
                .addValue("dpName", "%" + name + "%");
        return namedParameterJdbcTemplate.query(query,mapSqlParameterSource ,new BeanPropertyRowMapper<>(Department.class));
    }

}

