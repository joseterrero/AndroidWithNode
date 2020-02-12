package com.joseterrero.basicloginapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseContainer<T> {

    private List<T> rows;
    private long count;
}
