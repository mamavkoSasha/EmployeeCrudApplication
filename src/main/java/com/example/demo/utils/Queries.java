package com.example.demo.utils;

public interface Queries {

    @SqlResource.Path(pattern = "sql/departments/{0}.sql")
    enum Department implements SqlResource {
        INSERT,
        GET_ALL,
        GET_BY_ID,
        GET_BY_NAME,
        UPDATE,
        DELETE,
        COUNT_BY_NAME,
        TOTAL_COUNT,
        GET_ALL_PAGINATION
    }

    @SqlResource.Path(pattern = "sql/employees/{0}.sql")
    enum Employees implements SqlResource {
        INSERT,
        DELETE,
        UPDATE,
        GET_ALL_PAGINATION,
        GET_BY_NAME,
        GET_BY_ID,
        TOTAL_COUNT,
        GET_ALL
    }

    @SqlResource.Path(pattern = "sql/users/{0}.sql")
    enum Users implements SqlResource {
        INSERT,
        DELETE,
        UPDATE,
        SELECT_BY_EMAIL,
        SELECT_BY_USER_ID,
        SELECT_BY_USERNAME
    }
}
