package com.yilan.project.dto;

import lombok.Data;

@Data
public class PutItemRequest {
    private String name;
    private int price;
    private String describe;
    private int categoryId;
    private int quantity;
}
