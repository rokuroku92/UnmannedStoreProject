package com.yilan.project.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private int itemId;
    private int quantity;
    private int amount;
}
