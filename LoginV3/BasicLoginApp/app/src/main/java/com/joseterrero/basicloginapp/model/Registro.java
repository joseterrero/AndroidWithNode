package com.joseterrero.basicloginapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registro {

    private String name;
    private String email;
    private String password;
}
