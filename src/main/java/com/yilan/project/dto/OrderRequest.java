package com.yilan.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Integer accountId;
    private int paymentAmount;
    private List<OrderItemRequest> orderItemRequests;
}
