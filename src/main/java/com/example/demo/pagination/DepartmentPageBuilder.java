package com.example.demo.pagination;

import com.example.demo.exception.RestException;
import com.example.demo.models.Department;
import com.example.demo.models.DepartmentPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentPageBuilder {

    private static final int LIMIT = 5;

    public DepartmentPage buildPage(List<Department> allDepartments, int page){
        int endIndex = page * LIMIT;
        int startIndex = endIndex - LIMIT;
        int countOfDepartments = allDepartments.size();
        if(endIndex > countOfDepartments){
            endIndex = countOfDepartments;
        }

        List<Department> outputDepartments = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            outputDepartments.add(allDepartments.get(i));
        }

        int countPages = (int) Math.ceil(countOfDepartments / (double)LIMIT);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        if(outputDepartments.isEmpty()){
            throw new RestException("Page:" + page + "not found!", HttpStatus.NOT_FOUND);
        }
        return new DepartmentPage(outputDepartments, pages, countPages);
    }
}
