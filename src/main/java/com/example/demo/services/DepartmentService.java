package com.example.demo.services;

import com.example.demo.models.Department;
import com.example.demo.models.DepartmentPage;

import java.util.List;

public interface DepartmentService {
    DepartmentPage getAllDepartmentsPage(String name,int page);
    List<Department> getAllDepartments();
    void createDepartment(Department department);
    void deleteDepartmentById(int id);
    void editDepartment(Department department);
    Department getDepartmentById(int id);
}
