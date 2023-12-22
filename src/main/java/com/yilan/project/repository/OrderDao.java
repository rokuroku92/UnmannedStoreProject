package com.yilan.project.repository;

import com.yilan.project.model.Order;

import java.util.List;

public interface OrderDao {
    List<Order> queryAllOrder();
    void insertOrder(Integer accountId, String orderNumber, int paymentAmount);
    String selectLastOrderNumber();
}
