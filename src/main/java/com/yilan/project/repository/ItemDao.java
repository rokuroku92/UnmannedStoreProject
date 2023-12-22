package com.yilan.project.repository;

import com.yilan.project.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemDao {
    List<Item> queryAllItem();
    Item queryItem(int id);
    boolean insertItem(String name, int price, String describe, int categoryId, int quantity, MultipartFile img);
    boolean updateItem(Integer id, String name, int price, String describe, int categoryId, int quantity);
}
