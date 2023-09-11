package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.DepartmentPage;
import com.example.demo.models.Employee;
import com.example.demo.pagination.DepartmentPageBuilder;
import com.example.demo.rep.DepartmentRepos;
import com.example.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepos departmentRepos;
    private final DepartmentPageBuilder pageBuilder;

    public DepartmentServiceImpl(DepartmentRepos departmentRepos, DepartmentPageBuilder pageBuilder){
        this.departmentRepos = departmentRepos;
        this.pageBuilder = pageBuilder;
    }


    @Override
    public DepartmentPage getAllDepartmentsPage(String name, int page) {
        if(name != null){
            List<Department> allDep = departmentRepos.getDepartmentByName(name);
            if (allDep.isEmpty()) {
                throw new RestException("Department with name: " + name + " not found!", HttpStatus.NOT_FOUND);
            }
            return pageBuilder.buildPage(allDep, page);
        }
        List<Department> allDep = departmentRepos.getAllDepartments();
        return pageBuilder.buildPage(allDep,page);
    }


    @Override
    public List<Department> getAllDepartments() {
        return departmentRepos.getAllDepartments();
    }

    @Override
    public void createDepartment(Department department) {
        int count = departmentRepos.countByName(department.getDpName());
        if(count > 0){
            throw new RestException("This department already exists!", HttpStatus.BAD_REQUEST);
        }
        departmentRepos.saveDepartment(department);
    }

    @Override
    public void deleteDepartmentById(int id) {
        departmentRepos.deleteDepartmentById(id);
    }

    @Override
    public void editDepartment(Department department) {
        int count = departmentRepos.countByName(department.getDpName());
        if(count > 0){
            throw new RestException("This department already exists!", HttpStatus.BAD_REQUEST);
        }
        departmentRepos.editDepartment(department);
    }

    @Override
    public Department getDepartmentById(int id) {
       return departmentRepos.getDepartmentById(id);
    }
}
