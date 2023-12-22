package com.yilan.project.model;

import lombok.Data;

@Data
public class Item {
    private int id;
    private String name;
    private int price;
    private String describe;
    private int categoryId;
    private int quantity;
}
