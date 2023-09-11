package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeePage;
import com.example.demo.pagination.EmployeePageBuilder;
import com.example.demo.rep.EmployeeRepos;
import com.example.demo.rep.Implementation.EmployeeReposImpl;
import com.example.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EmployeeServiceImpl implements EmployeeService {
private final EmployeeRepos employeeRepos;
private final EmployeePageBuilder pageBuilder;

public EmployeeServiceImpl(EmployeeRepos employeeRepos, EmployeePageBuilder pageBuilder){
    this.employeeRepos = employeeRepos;
    this.pageBuilder = pageBuilder;
}

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepos.getAllEmployee();
    }

    @Override
    public EmployeePage getAllEmployeesPage(String name, int page) {
        if(name != null){
            List<Employee> allEmp = employeeRepos.getEmployeeByName(name);
            if (allEmp.isEmpty()) {
                throw new RestException("Employees with name: " + name + " not found!", HttpStatus.NOT_FOUND);
            }
            return pageBuilder.buildPage(allEmp, page);
        }
        List<Employee> allEmp = employeeRepos.getAllEmployee();
        return pageBuilder.buildPage(allEmp, page);
    }

    @Override
    public void createEmployee(Employee employee) {
    if(employee.getAge() <= 17){
        throw new RestException("Incorrectly entered field. The worker must be at least 18 years old.", HttpStatus.BAD_REQUEST);
    }
        employeeRepos.saveEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepos.deleteEmployeeById(id);
    }

    @Override
    public void editEmployee(Employee employee) {
        if(employee.getAge() <= 17){
            throw new RestException("Incorrectly entered field. The worker must be at least 18 years old.", HttpStatus.BAD_REQUEST);
        }
        employeeRepos.editEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepos.getEmployeeById(id);
    }
}
