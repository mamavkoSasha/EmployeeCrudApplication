package com.example.demo.rep;


import com.example.demo.models.Employee;

import java.util.List;

public interface EmployeeRepos {
    List<Employee> getAllEmployee();
    void saveEmployee(Employee employee);
    void deleteEmployeeById(int id);
    void editEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getEmployeeByName(String name);
}
