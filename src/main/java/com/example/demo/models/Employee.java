package com.example.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Employee {
    private int empID;
    private String empName;
    private String empActive;
    private String departmentName;
    private int depId;
    private String date;
    private int age;
    private String city;
}
