package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Employee;
import com.example.demo.models.Pagination;
import com.example.demo.rep.EmployeeRepos;
import com.example.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constants.Constants.Messages.AGE_LESS_THAN_18;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepos employeeRepos;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepos.getAllEmployee();
    }

    @Override
    public Pagination<Employee> getAllDepartmentsPagination(String name, int pageSize, int currentPage) {

        Long countOfEmployees = employeeRepos.totalCount();

        if (countOfEmployees == 0) {
            return Pagination.<Employee>builder()
                    .data(List.of())
                    .totalCount(0)
                    .hasNextPage(false)
                    .hasPreviousPage(false)
                    .build();
        }

        List<Employee> employees = employeeRepos.getAllEmployeesPagination(pageSize, currentPage);

        boolean hasNextPage = countOfEmployees > (currentPage + pageSize);
        boolean hasPreviousPage = currentPage > 1;

        int totalPages = (int) ((countOfEmployees + pageSize - 1) / pageSize);

        if (name != null) {

            List<Employee> searchEmployees = employeeRepos.getEmployeeByName(name, pageSize, currentPage);

            return Pagination.<Employee>builder()
                    .data(searchEmployees)
                    .totalCount(Math.toIntExact(countOfEmployees))
                    .hasNextPage(hasNextPage)
                    .hasPreviousPage(hasPreviousPage)
                    .totalPages(totalPages)
                    .build();
        }

        return Pagination.<Employee>builder()
                .data(employees)
                .totalCount(Math.toIntExact(countOfEmployees))
                .hasNextPage(hasNextPage)
                .hasPreviousPage(hasPreviousPage)
                .totalPages(totalPages)
                .build();
    }


    @Override
    public void createEmployee(Employee employee) {

        if (employee.getAge() <= 17) {

            throw new RestException(AGE_LESS_THAN_18, HttpStatus.BAD_REQUEST);
        }

        employeeRepos.saveEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepos.deleteEmployeeById(id);
    }

    @Override
    public void editEmployee(Employee employee) {

        if (employee.getAge() <= 17) {

            throw new RestException(AGE_LESS_THAN_18, HttpStatus.BAD_REQUEST);
        }

        employeeRepos.editEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepos.getEmployeeById(id);
    }
}
