package com.yilan.project.service;

import com.yilan.project.dto.PutItemRequest;
import com.yilan.project.dto.UpdateItemRequest;
import com.yilan.project.model.Item;
import com.yilan.project.model.Order;
import com.yilan.project.repository.ItemDao;
import com.yilan.project.repository.OrderDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderDao orderDao;


    public List<Item> getAllItem(){
        return itemDao.queryAllItem();
    }

    public List<Order> getAllOrders(){
        return orderDao.queryAllOrder();
    }

    public String putItem(@NotNull PutItemRequest putItemRequest, MultipartFile img){
        return itemDao.insertItem(putItemRequest.getName(), putItemRequest.getPrice(),
                putItemRequest.getDescribe(), putItemRequest.getCategoryId(), putItemRequest.getQuantity(), img)
                ? "OK" : "FAIL";
    }

    public String updateItem(@NotNull UpdateItemRequest updateItemRequest){
        return itemDao.updateItem(updateItemRequest.getId(), updateItemRequest.getName(), updateItemRequest.getPrice(),
                updateItemRequest.getDescribe(), updateItemRequest.getCategoryId(), updateItemRequest.getQuantity())
                ? "OK" : "FAIL";
    }
}
