package com.yilan.project.model;

import lombok.Data;

@Data
public class Order {
    private int id;
    private String account;
    private String orderNumber;
    private int paymentAmount;
    private String createTime;
}
