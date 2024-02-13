package com.example.demo.controllers;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.Pagination;
import com.example.demo.services.DepartmentService;
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
import static com.example.demo.constants.Constants.Strings.DEPARTMENT;
import static com.example.demo.constants.Constants.Strings.DEPARTMENTS;
import static com.example.demo.constants.Constants.Strings.DP_ID;
import static com.example.demo.constants.Constants.Strings.NAME;
import static com.example.demo.constants.Constants.Strings.PAGE;
import static com.example.demo.constants.Constants.Strings.PAGE_SIZE;
import static com.example.demo.constants.Constants.Strings.PAGINATION;
import static com.example.demo.constants.Constants.Strings.SIZE;
import static com.example.demo.constants.Constants.Strings.WARNING_MESSAGE;


@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public String getAllDepartmentsPagination(@RequestParam(name = PAGE, defaultValue = "1") int currentPage,
                                              @RequestParam(name = SIZE, defaultValue = "5") int pageSize,
                                              @RequestParam(name = NAME, required = false) String name,
                                              Model model) {
        try {
            Pagination<Department> pagination = departmentService.getAllDepartmentsPagination(name, pageSize, currentPage);

            model.addAttribute(PAGINATION, pagination);
            model.addAttribute(PAGE_SIZE, pageSize);
            model.addAttribute(CURRENT_PAGE, currentPage);

        } catch (RestException exception) {
            model.addAttribute(WARNING_MESSAGE, exception.getMessage());

            return "all-departments";
        }
        return "all-departments";
    }


    @GetMapping("/create/new")
    public String createDepartment(Model model) {

        model.addAttribute(DEPARTMENT, new Department());

        return "create-Department";
    }

    @PostMapping("/create")
    public String postCreateDepartment(Department department, Model model) {

        try {
            departmentService.createDepartment(department);
        } catch (RestException exception) {
            model.addAttribute(WARNING_MESSAGE, exception.getMessage());
            model.addAttribute(DEPARTMENTS, department);
            return "create-Department";
        }

        return "redirect:/department/all";
    }

    @PostMapping("/delete/{dpID}")
    public String deleteDepartmentById(@PathVariable(DP_ID) int id) {

        departmentService.deleteDepartmentById(id);

        return "redirect:/department/all";
    }

    @GetMapping("/edit/{id}")
    public String getEditDepartment(@PathVariable int id, Model model) {

        Department department = departmentService.getDepartmentById(id);

        model.addAttribute(DEPARTMENT, department);

        return "edit-department";
    }

    @PostMapping("/edit")
    public String postEditDepartment(@ModelAttribute(DEPARTMENT) Department department, Model model) {

        try {
            departmentService.editDepartment(department);
        } catch (RestException exception) {
            model.addAttribute(WARNING_MESSAGE, exception.getMessage());
            model.addAttribute(DEPARTMENTS, department);
            return "edit-department";
        }

        return "redirect:/department/all";
    }

    @GetMapping("/view/{id}")
    public String viewDepartment(@PathVariable int id, Model model) {

        Department department = departmentService.getDepartmentById(id);

        model.addAttribute(DEPARTMENT, department);

        return "view-departments";
    }
}
