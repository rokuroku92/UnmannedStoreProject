package com.yilan.project.repository.Impl;

import com.yilan.project.model.Order;
import com.yilan.project.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> queryAllOrder(){
        String sql = "SELECT oh.id, ai.username AS username, oh.order_number, oh.payment_amount, oh.create_time " +
                    "FROM order_history oh INNER JOIN account_info ai ON oh.account_id = ai.id ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public void insertOrder(Integer accountId, String orderNumber, int paymentAmount){
        String sql = "INSERT INTO `order_history` (`account_id`, `order_number`, `payment_amount`) VALUES " +
                "(?, ?, ?)";
        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
        jdbcTemplate.update(sql, accountId, orderNumber, paymentAmount);
    }

    @Override
    public String selectLastOrderNumber(){
        String sql = "SELECT `order_number` FROM `order_history` WHERE `order_number` LIKE 'ORD%' ORDER BY order_number DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

}
