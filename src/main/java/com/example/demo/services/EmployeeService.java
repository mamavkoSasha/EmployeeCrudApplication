package com.example.demo.services;

import com.example.demo.models.Employee;
import com.example.demo.models.EmployeePage;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    EmployeePage getAllEmployeesPage(String name, int page);
    void createEmployee(Employee employee);
    void deleteEmployeeById(int id);
    void editEmployee(Employee employee);
    Employee getEmployeeById(int id);
}
