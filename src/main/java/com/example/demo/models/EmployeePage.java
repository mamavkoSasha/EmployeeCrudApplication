package com.example.demo.models;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeePage {
    private List<Employee> outputEmployees;
    private List<Integer> pageIndexes;
    private int countOfPages;

    public EmployeePage(List<Employee> outputEmployees, List<Integer> pageIndexes, int countOfPages) {
        this.outputEmployees = outputEmployees;
        this.pageIndexes = pageIndexes;
        this.countOfPages = countOfPages;
    }

}
