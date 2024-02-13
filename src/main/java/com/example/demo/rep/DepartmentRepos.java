package com.example.demo.rep;

import com.example.demo.models.Department;

import java.util.List;

public interface DepartmentRepos {
    List<Department> getAllDepartments();

    List<Department> getAllDepartmentsPagination(int pageSize, int currentPage);

    void saveDepartment(Department department);

    void deleteDepartmentById(int id);

    void editDepartment(Department department);

    Department getDepartmentById(int id);

    Integer countByName(String dpName);

    List<Department> getDepartmentByName(String name, int pageSize, int currentPage);

    Long totalCount();
}
