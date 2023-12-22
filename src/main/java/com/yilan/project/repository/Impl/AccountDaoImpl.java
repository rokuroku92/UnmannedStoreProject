package com.yilan.project.repository.Impl;

import com.yilan.project.model.Account;
import com.yilan.project.model.Category;
import com.yilan.project.model.Item;
import com.yilan.project.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account authenticate(String inputUsername, String inputPassword) {
        String sql = "SELECT * FROM `account_info` WHERE `username` = ?";

        List<Account> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class), inputUsername);

        if (!result.isEmpty()) {
            Account user = result.get(0);
            if (inputPassword.equals(user.getPassword())) {
                return user;  // 驗證成功
            }
        }
        return null;  // 驗證失敗
    }

    @Override
    public boolean insertAccount(String username, String password, String phoneNumber){
        String sql = "INSERT INTO `account_info` (`username`, `password`, `phone_number`, `level`) VALUES " +
                "(?, ?, ?, 1);";
        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
        int rowsAffected = jdbcTemplate.update(sql, username, password, phoneNumber);
        return (rowsAffected > 0);
    }


}
