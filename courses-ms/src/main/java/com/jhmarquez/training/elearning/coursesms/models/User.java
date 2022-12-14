package com.jhmarquez.training.elearning.coursesms.models;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;
}
