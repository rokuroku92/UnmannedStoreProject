package com.yilan.project.model;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String username;
    private String password;
    private String phoneNumber;
    private int level;
}
