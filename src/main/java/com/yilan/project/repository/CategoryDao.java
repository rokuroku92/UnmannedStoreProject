package com.yilan.project.repository;

import com.yilan.project.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> queryAllCategory();
    void insertCategory(String name, String memo);
}
