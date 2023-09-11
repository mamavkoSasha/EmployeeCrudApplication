package com.example.demo.pagination;

import com.example.demo.exception.RestException;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeePage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeePageBuilder {
    private static final int LIMIT = 5;

    public EmployeePage buildPage(List<Employee> allEmployees, int page){
        int endIndex = page * LIMIT;
        int startIndex = endIndex - LIMIT;
        int countOfEmployees = allEmployees.size();
        if(endIndex > countOfEmployees){
            endIndex = countOfEmployees;
        }

        List<Employee> outputEmployees = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            outputEmployees.add(allEmployees.get(i));
        }

        int countPages = (int) Math.ceil(countOfEmployees / (double)LIMIT);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        if(outputEmployees.isEmpty()){
            throw new RestException("Page:" + page + "not found!", HttpStatus.NOT_FOUND);
        }
        return new EmployeePage(outputEmployees, pages, countPages);
    }
}
