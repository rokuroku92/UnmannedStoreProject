package com.yilan.project.service;

import com.yilan.project.dto.OrderRequest;
import com.yilan.project.model.Account;
import com.yilan.project.model.Item;
import com.yilan.project.repository.AccountDao;
import com.yilan.project.repository.ItemDao;
import com.yilan.project.repository.OrderDao;
import com.yilan.project.repository.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ItemDao itemDao;

    private Integer lastOrderNumber;

    public Account authenticate(String username, String password){
        return accountDao.authenticate(username, password);
    }

    public boolean signUp(String username, String password, String phoneNumber){
        return accountDao.insertAccount(username, password, phoneNumber);
    }

    public Item getItemById(Integer id){
        if(id==null){
            return null;
        }
        return itemDao.queryItem(id);
    }

    public String payOrder(OrderRequest orderRequest){
        if(orderRequest.getAccountId() == null){
            return "帳號資訊錯誤！";
        } else if (orderRequest.getOrderItemRequests().size() == 0){
            return "無購物品項";
        }

        String orderNumber = getPureTaskNumber();
        orderDao.insertOrder(orderRequest.getAccountId(), orderNumber, orderRequest.getPaymentAmount());
        orderRequest.getOrderItemRequests().forEach(orderItemRequest -> {
            orderDetailDao.insertOrderDetail(orderNumber, orderItemRequest.getItemId(), orderItemRequest.getQuantity(), orderItemRequest.getAmount());
        });
        return "OK";
    }

    private String getPureTaskNumber(){

        if (lastOrderNumber == null) {
            // 伺服器重啟
            String lastOrderNumberS = orderDao.selectLastOrderNumber();
            lastOrderNumber = Integer.parseInt(lastOrderNumberS.substring(3));
        }
        lastOrderNumber++;
        return "ORD" + String.format("%03d", lastOrderNumber);
    }
}
