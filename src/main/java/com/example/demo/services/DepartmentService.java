package com.example.demo.services;

import com.example.demo.models.Department;
import com.example.demo.models.Pagination;

import java.util.List;

public interface DepartmentService {
    Pagination<Department> getAllDepartmentsPagination(String name, int pageSize, int currentPage);

    List<Department> getAllDepartments();

    void createDepartment(Department department);

    void deleteDepartmentById(int id);

    void editDepartment(Department department);

    Department getDepartmentById(int id);
}
