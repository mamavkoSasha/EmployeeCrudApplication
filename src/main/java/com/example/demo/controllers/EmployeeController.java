package com.example.demo.controllers;

import com.example.demo.exception.RestException;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeePage;
import com.example.demo.services.DepartmentService;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.Implementation.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/all-employees")
    public String getAllEmployees(@RequestParam(value="page", defaultValue = "1") int page,
                                  @RequestParam (name = "name", required = false) String name, Model model){
        try {
            EmployeePage employeePage = employeeService.getAllEmployeesPage(name, page);
            model.addAttribute("employeeList", employeePage.getOutputEmployees());
            model.addAttribute("pages", employeePage.getPageIndexes());
            model.addAttribute("currentPage", page);
            model.addAttribute("countOfPages", employeePage.getCountOfPages());
        }catch (RestException exception){
            model.addAttribute("warningMessage", exception.getMessage());
            return "view-all-employees";
        }

        return "view-all-employees";
    }

    @GetMapping("/create/Employee")
    public String createEmployee(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "create-Employee";
    }

    @PostMapping("/employee/create")
    public String postCreateEmployee(Employee employee, Model model){
        try {
            if(employee.getEmpActive() == null){
                employee.setEmpActive("No");
            }

            employeeService.createEmployee(employee);
        } catch (RestException exception){
           model.addAttribute("warningMessage", exception.getMessage());
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "create-Employee";
        }

        return "redirect:/all-employees";
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployeeById(@PathVariable("id") int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/all-employees";
    }

    @GetMapping("/employee/edit/{id}")
    public String getEditEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "edit-employee";
    }

    @PostMapping("/employee/edit")
    public String postEditEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        try {
            if (employee.getEmpActive() == null) {
                employee.setEmpActive("No");
            }

            employeeService.editEmployee(employee);
        } catch (RestException exception) {
            model.addAttribute("warningMessage", exception.getMessage());
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "edit-employee";
        }
        return "redirect:/all-employees";
    }

    @GetMapping("/employee/view/{id}")
    public String viewEmployee(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "view-employee";
    }

}
