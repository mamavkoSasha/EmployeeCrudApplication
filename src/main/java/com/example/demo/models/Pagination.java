package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    private List<T> data;
    private int totalCount;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int totalPages;
}
