package com.example.demo.rep.Implementation;

import com.example.demo.models.Department;
import com.example.demo.models.Employee;
import com.example.demo.rep.EmployeeRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class EmployeeReposImpl implements EmployeeRepos {

private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

public EmployeeReposImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
}


    @Override
    public List<Employee> getAllEmployee() {
        String query = "SELECT p.empID AS empID,\n" +
                "       p.empName AS empName,\n" +
                "       p.empActive AS empActive,\n" +
                "       p.date AS date,\n" +
                "       p.age AS age,\n" +
                "       p.city AS city,\n" +
                "       d.dpName AS departmentName \n" +
                "FROM employees p \n"+
                "JOIN departments d on p.emp_dpID = d.dpID";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public void saveEmployee(Employee employee) {
        String query = "INSERT INTO employees (empID, empName, empActive, emp_dpID, date, age, city) VALUES (:empID, :empName, :empActive, :emp_dpID, :date, :age, :city)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("empID", employee.getEmpID())
                .addValue("empName", employee.getEmpName())
                .addValue("empActive", employee.getEmpActive())
                .addValue("emp_dpID", employee.getDepId())
                .addValue("date", employee.getDate())
                .addValue("age", employee.getAge())
                .addValue("city", employee.getCity());

        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteEmployeeById(int id) {
        String query = "DELETE FROM employees WHERE empID = :empID";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("empID", id);
        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void editEmployee(Employee employee) {
        String query = "UPDATE employees " +
                "SET empName = :empName, emp_dpID = :emp_dpID, date = :date, age = :age, city = :city, empActive = :empActive " +
                "WHERE empID = :empID ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("empID", employee.getEmpID())
                .addValue("empName", employee.getEmpName())
                .addValue("emp_dpID", employee.getDepId())
                .addValue("date", employee.getDate())
                .addValue("age", employee.getAge())
                .addValue("city", employee.getCity())
                .addValue("empActive", employee.getEmpActive());
        namedParameterJdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public Employee getEmployeeById(int id) {
        String query = "SELECT p.empID AS empID,\n" +
                "       p.empName AS empName,\n" +
                "       p.empActive AS empActive,\n" +
                "       p.date AS date,\n" +
                "       p.age AS age,\n" +
                "       p.city AS city,\n" +
                "       d.dpName AS departmentName \n" +
                "FROM employees p \n"+
                "JOIN departments d on p.emp_dpID = d.dpID \n" +
                "WHERE empID = :empID";

        MapSqlParameterSource mapSqlParameterSource =new MapSqlParameterSource()
                .addValue("empID", id);
        return namedParameterJdbcTemplate.queryForObject(query,mapSqlParameterSource ,new BeanPropertyRowMapper<>(Employee.class));
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        String query = "SELECT p.empID AS empID,\n" +
                "       p.empName AS empName,\n" +
                "       p.empActive AS empActive,\n" +
                "       p.date AS date,\n" +
                "       p.age AS age,\n" +
                "       p.city AS city,\n" +
                "       d.dpName AS departmentName \n" +
                "FROM employees p \n"+
                "JOIN departments d on p.emp_dpID = d.dpID " +
                "WHERE empName LIKE :empName";

        MapSqlParameterSource mapSqlParameterSource =new MapSqlParameterSource()
                .addValue("empName", "%" + name + "%");
        return namedParameterJdbcTemplate.query(query,mapSqlParameterSource ,new BeanPropertyRowMapper<>(Employee.class));
    }
}
