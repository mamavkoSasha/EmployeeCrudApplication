package com.example.demo.services;

import com.example.demo.models.Employee;
import com.example.demo.models.Pagination;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Pagination<Employee> getAllDepartmentsPagination(String name, int pageSize, int currentPage);

    void createEmployee(Employee employee);

    void deleteEmployeeById(int id);

    void editEmployee(Employee employee);

    Employee getEmployeeById(int id);
}
