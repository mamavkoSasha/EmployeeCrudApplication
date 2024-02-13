package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.Pagination;
import com.example.demo.rep.DepartmentRepos;
import com.example.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.Constants.Messages.DEPARTMENT_EXIST;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepos departmentRepos;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepos.getAllDepartments();
    }

    @Override
    public Pagination<Department> getAllDepartmentsPagination(String name, int pageSize, int currentPage) {

        Long countOfDepartments = departmentRepos.totalCount();

        if (countOfDepartments == 0) {
            return Pagination.<Department>builder()
                    .data(List.of())
                    .totalCount(0)
                    .hasNextPage(false)
                    .hasPreviousPage(false)
                    .build();
        }

        List<Department> departments = departmentRepos.getAllDepartmentsPagination(pageSize, currentPage);

        boolean hasNextPage = countOfDepartments > (currentPage + pageSize);
        boolean hasPreviousPage = currentPage > 1;

        int totalPages = (int) ((countOfDepartments + pageSize - 1) / pageSize);

        if (name != null) {
            List<Department> searchDepartments = departmentRepos.getDepartmentByName(name, pageSize, currentPage);

            return Pagination.<Department>builder()
                    .data(searchDepartments)
                    .totalCount(Math.toIntExact(countOfDepartments))
                    .hasNextPage(hasNextPage)
                    .hasPreviousPage(hasPreviousPage)
                    .totalPages(totalPages)
                    .build();
        }

        return Pagination.<Department>builder()
                .data(departments)
                .totalCount(Math.toIntExact(countOfDepartments))
                .hasNextPage(hasNextPage)
                .hasPreviousPage(hasPreviousPage)
                .totalPages(totalPages)
                .build();
    }

    @Override
    public void createDepartment(Department department) {

        int count = departmentRepos.countByName(department.getDpName());

        if (count > 0) {

            throw new RestException(DEPARTMENT_EXIST, HttpStatus.BAD_REQUEST);
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

        if (count > 0) {

            throw new RestException(DEPARTMENT_EXIST, HttpStatus.BAD_REQUEST);
        }
        departmentRepos.editDepartment(department);
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepos.getDepartmentById(id);
    }
}
