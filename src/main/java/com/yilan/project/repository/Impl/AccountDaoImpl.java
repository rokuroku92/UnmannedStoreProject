package com.yilan.project.repository.Impl;

import com.yilan.project.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int authenticate(String inputAccount, String inputPassword) {
        String sql = "SELECT * FROM `account_info` WHERE `account` = ?";

        Integer result = jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next() && inputPassword.equals(resultSet.getString("password"))) {
                return resultSet.getInt("id");  // 驗證成功，返回 id
            } else {
                return -1;  // 驗證失敗，返回 -1
            }
        }, inputAccount);

        return (result != null) ? result : -1;
    }


}
