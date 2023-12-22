package com.yilan.project.repository.Impl;

import com.yilan.project.model.Item;
import com.yilan.project.model.OrderDetail;
import com.yilan.project.repository.OrderDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailDaoImpl implements OrderDetailDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderDetail> queryAllOrderDetail(){
        String sql = "SELECT * FROM `order_detail_history`";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class));
    }

    @Override
    public List<OrderDetail> queryOrderDetailByOrderNumber(String orderNumber){
        String sql = "SELECT * FROM `order_detail_history` WHERE `order_number` = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class), orderNumber);
    }

    @Override
    public void insertOrderDetail(String orderNumber, int itemId, int quantity, int amount){
        String sql = "INSERT INTO `order_detail_history` (`order_number`, `item_id`, `quantity`, `amount`) VALUES " +
                "(?, ?, ?, ?)";
        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
        jdbcTemplate.update(sql, orderNumber, itemId, quantity, amount);
    }

}
