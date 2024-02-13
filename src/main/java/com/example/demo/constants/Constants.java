package com.example.demo.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class SqlParams {

        public static final String DEPARTMENT_ID = "dpID";
        public static final String DEPARTMENT_NAME = "dpName";
        public static final String EMPLOYEE_ID = "empID";
        public static final String EMPLOYEE_NAME = "empName";
        public static final String DATE = "date";
        public static final String AGE = "age";
        public static final String CITY = "city";
        public static final String EMPLOYEE_ACTIVE = "empActive";
        public static final String EMPLOYEE_DEPARTMENT_ID = "emp_dpID";
        public static final String PAGE_SIZE = "pageSize";
        public static final String CURRENT_PAGE = "currentPage";
    }

    @UtilityClass
    public static class Strings {
        public static final String PAGINATION = "pagination";
        public static final String PAGE_SIZE = "pageSize";
        public static final String CURRENT_PAGE = "currentPage";
        public static final String WARNING_MESSAGE = "warningMessage";
        public static final String DEPARTMENT = "department";
        public static final String DEPARTMENTS = "departments";
        public static final String DP_ID = "dpID";
        public static final String PAGE = "page";
        public static final String SIZE = "size";
        public static final String NAME = "name";
        public static final String EMPLOYEE = "employee";
        public static final String ID = "id";
        public static final String NO = "No";
    }

    public static class Messages {
        public static final String DEPARTMENT_EXIST = "This department already exists!";
        public static final String AGE_LESS_THAN_18 = "Incorrectly entered field. The worker must be at least 18 years old.";
    }
}
