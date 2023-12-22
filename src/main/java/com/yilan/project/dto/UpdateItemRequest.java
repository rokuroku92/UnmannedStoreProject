package com.yilan.project.dto;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private int id;
    private String name;
    private int price;
    private String describe;
    private int categoryId;
    private int quantity;
}
