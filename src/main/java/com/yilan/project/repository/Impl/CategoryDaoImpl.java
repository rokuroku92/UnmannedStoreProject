package com.yilan.project.repository.Impl;

import com.yilan.project.model.Category;
import com.yilan.project.repository.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> queryAllCategory(){
        String sql = "SELECT * FROM `category`";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public void insertCategory(String name, String memo){
        String sql = "INSERT INTO `category` (`name`, `memo`) VALUES (?, ?)";
        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
        jdbcTemplate.update(sql, name, memo);
    }
}
