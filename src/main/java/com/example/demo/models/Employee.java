package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
