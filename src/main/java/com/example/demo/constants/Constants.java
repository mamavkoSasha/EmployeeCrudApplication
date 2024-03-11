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
        public static final String USER_ID = "userId";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
    }

    @UtilityClass
    public static class Strings {

        public static final String USER_ID = "user_id";
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
        //claims
        public static final String ACCOUNT_TYPE_PROPERTY = "atc";
        public static final String ALGORITHM_PROPERTY = "alg";
        public static final String SUBJECT_PROPERTY = "sub";
        public static final String ISSUED_TIMESTAMP_PROPERTY = "iat";
        public static final String EXPIRATION_TIMESTAMP_PROPERTY = "exp";
        public static final String ENCODING_ALGORITHM = "HS512";
        public static final String SAT_COOKIE = "SAT";
    }

    public static class Messages {
        public static final String DEPARTMENT_EXIST = "This department already exists!";
        public static final String AGE_LESS_THAN_18 = "Incorrectly entered field. The worker must be at least 18 years old.";
        public static final String FAILED_REGISTRATION = "Failed to register user!";
        public static final String INVALID_REGISTER_INFO = "Invalid registration information!";
        public static final String FAILED_AUTHORISATION = "Failed to authorise user!";
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String VEHICLE_NOT_FOUND = "Vehicle not found!";
        public static final String PAGE_NOT_FOUND = "Page not found!";
        public static final String UNAUTHORISED_USER_REQUEST = "User not allowed to perform this request!";
        public static final String REGISTRATION_SUCCESSFUL = "Registration successful!";
        public static final String AUTHORISATION_SUCCESSFUL = "Authorisation successful!";
        public static final String REPAIR_STATION_NOT_FOUND = "Repair station not found!";
    }

    @UtilityClass
    public static class Numbers {

        public static final long EXPIRATION_TIME = 86_400_000;

    }
}
