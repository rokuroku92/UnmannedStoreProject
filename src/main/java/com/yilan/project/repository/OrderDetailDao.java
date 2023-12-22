package com.yilan.project.repository;

import com.yilan.project.model.OrderDetail;

import java.util.List;

public interface OrderDetailDao {
    List<OrderDetail> queryAllOrderDetail();
    List<OrderDetail> queryOrderDetailByOrderNumber(String orderNumber);
    void insertOrderDetail(String orderNumber, int itemId, int quantity, int amount);
}
