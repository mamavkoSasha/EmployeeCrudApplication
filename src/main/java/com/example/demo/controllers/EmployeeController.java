package com.example.demo.controllers;

import com.example.demo.exception.RestException;
import com.example.demo.models.Employee;
import com.example.demo.models.Pagination;
import com.example.demo.services.DepartmentService;
import com.example.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.demo.constants.Constants.Strings.CURRENT_PAGE;
import static com.example.demo.constants.Constants.Strings.DEPARTMENTS;
import static com.example.demo.constants.Constants.Strings.EMPLOYEE;
import static com.example.demo.constants.Constants.Strings.ID;
import static com.example.demo.constants.Constants.Strings.NAME;
import static com.example.demo.constants.Constants.Strings.NO;
import static com.example.demo.constants.Constants.Strings.PAGE;
import static com.example.demo.constants.Constants.Strings.PAGE_SIZE;
import static com.example.demo.constants.Constants.Strings.PAGINATION;
import static com.example.demo.constants.Constants.Strings.SIZE;
import static com.example.demo.constants.Constants.Strings.WARNING_MESSAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/all")
    public String getAllEmployeesPagination(@RequestParam(name = PAGE, defaultValue = "1") int currentPage,
                                            @RequestParam(name = SIZE, defaultValue = "5") int pageSize,
                                            @RequestParam(name = NAME, required = false) String name,
                                            Model model) {
        try {

            Pagination<Employee> pagination = employeeService.getAllDepartmentsPagination(name, pageSize, currentPage);

            model.addAttribute(PAGINATION, pagination);
            model.addAttribute(PAGE_SIZE, pageSize);
            model.addAttribute(CURRENT_PAGE, currentPage);

        } catch (RestException exception) {
            model.addAttribute(WARNING_MESSAGE, exception.getMessage());

            return "all-employees";
        }
        return "all-employees";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {

        model.addAttribute(EMPLOYEE, new Employee());
        model.addAttribute(DEPARTMENTS, departmentService.getAllDepartments());

        return "create-Employee";
    }

    @PostMapping("/create/new")
    public String postCreateEmployee(Employee employee, Model model) {

        try {

            if (employee.getEmpActive() == null) {
                employee.setEmpActive(NO);
            }

            employeeService.createEmployee(employee);
        } catch (RestException exception) {

            model.addAttribute(WARNING_MESSAGE, exception.getMessage());
            model.addAttribute(EMPLOYEE, employee);
            model.addAttribute(DEPARTMENTS, departmentService.getAllDepartments());
            return "create-Employee";
        }

        return "redirect:/employee/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable(ID) int id) {

        employeeService.deleteEmployeeById(id);

        return "redirect:/employee/all";
    }

    @GetMapping("/edit/{id}")
    public String getEditEmployee(@PathVariable int id, Model model) {

        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute(EMPLOYEE, employee);
        model.addAttribute(DEPARTMENTS, departmentService.getAllDepartments());

        return "edit-employee";
    }

    @PostMapping("/edit")
    public String postEditEmployee(@ModelAttribute(EMPLOYEE) Employee employee, Model model) {

        try {

            if (employee.getEmpActive() == null) {
                employee.setEmpActive(NO);
            }

            employeeService.editEmployee(employee);
        } catch (RestException exception) {

            model.addAttribute(WARNING_MESSAGE, exception.getMessage());
            model.addAttribute(EMPLOYEE, employee);
            model.addAttribute(DEPARTMENTS, departmentService.getAllDepartments());

            return "edit-employee";
        }
        return "redirect:/employee/all";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable int id, Model model) {

        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute(EMPLOYEE, employee);

        return "view-employee";
    }
}
