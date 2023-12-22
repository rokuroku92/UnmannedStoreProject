package com.yilan.project.model;

import lombok.Data;

@Data
public class OrderDetail {
    private int id;
    private String orderNumber;
    private int itemId;
    private int quantity;
    private int amount;
}
