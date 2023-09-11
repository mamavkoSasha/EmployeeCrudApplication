package com.example.demo.controllers;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.DepartmentPage;
import com.example.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class DepartmentController {

private final DepartmentService departmentService;

    @GetMapping("/all-departments")
    public String getAllDepartments(@RequestParam (value="page", defaultValue = "1") int page,
                                    @RequestParam (name = "name", required = false) String name,Model model){
        try {
            DepartmentPage departmentPage = departmentService.getAllDepartmentsPage(name, page);
            model.addAttribute("departmentList", departmentPage.getOutputDepartments());
            model.addAttribute("pages", departmentPage.getPageIndexes());
            model.addAttribute("currentPage", page);
            model.addAttribute("countOfPages", departmentPage.getCountOfPages());
        } catch (RestException exception){
            model.addAttribute("warningMessage", exception.getMessage());
            return "view-all-departments";
        }
        return "view-all-departments";
    }

    @GetMapping("/create/Department")
    public String createDepartment(Model model){
        model.addAttribute("department", new Department());
        return "create-Department";
    }

    @PostMapping("/department/create")
    public String postCreateDepartment(Department department, Model model){
        try {
            departmentService.createDepartment(department);
        } catch (RestException exception){
            model.addAttribute("warningMessage", exception.getMessage());
            model.addAttribute("departments", department);
            return "create-Department";
        }

        return "redirect:/all-departments";
    }

    @PostMapping("/department/delete/{dpID}")
    public String deleteDepartmentById(@PathVariable("dpID") int id){
        departmentService.deleteDepartmentById(id);
        return "redirect:/all-departments";
    }

    @GetMapping("/department/edit/{id}")
    public String getEditDepartment(@PathVariable int id, Model model){
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        return "edit-department";
    }

    @PostMapping("/department/edit")
    public String postEditDepartment(@ModelAttribute("department") Department department,Model model){
        try {
            departmentService.editDepartment(department);
        } catch (RestException exception){
            model.addAttribute("warningMessage", exception.getMessage());
            model.addAttribute("departments", department);
            return "edit-department";
        }
        return "redirect:/all-departments";
    }

    @GetMapping("/department/view/{id}")
    public String viewDepartment(@PathVariable int id, Model model){
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        return "view-departments";
    }
}
