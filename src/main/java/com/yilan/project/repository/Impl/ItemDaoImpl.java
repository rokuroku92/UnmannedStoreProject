package com.yilan.project.repository.Impl;

import com.yilan.project.model.Item;
import com.yilan.project.repository.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Item> queryAllItem(){
        String sql = "SELECT * FROM `item`";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public Item queryItem(int id){
        String sql = "SELECT * FROM `item` WHERE `id` = ?";
        List<Item> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Item.class), id);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;  // 驗證失敗
    }

//    @Override
//    public boolean insertItem(String name, int price, String describe, int categoryId, int quantity, MultipartFile img){
//        String sql = "INSERT INTO `item` (`name`, `price`, `describe`, `category_id`, `quantity`) VALUES " +
//                "(?, ?, ?, ?, ?)";
//        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
//        int rowsAffected = jdbcTemplate.update(sql, name, price, describe, categoryId, quantity);
//        if(rowsAffected > 0){
//            if (img != null && !img.isEmpty()) {
//                try {
//
//                    // 构建文件保存路径
//                    Path filePath = Paths.get(uploadDir, "product-"+id);
//                    // 保存文件到指定路径
//                    img.transferTo(filePath);
//                    return true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//        }
//    }

    @Override
    public boolean insertItem(String name, int price, String describe, int categoryId, int quantity, MultipartFile img) {
        // SQL语句
        String sql = "INSERT INTO `item` (`name`, `price`, `describe`, `category_id`, `quantity`) VALUES (?, ?, ?, ?, ?)";

        // 定义参数值和参数类型
        Object[] params = {name, price, describe, categoryId, quantity};
        int[] types = {Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER};

        // 创建 PreparedStatementCreatorFactory
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, types);
        // 设置返回自动生成的键
        factory.setReturnGeneratedKeys(true);
        // 设置自动生成的键的列名（根据实际情况修改为生成主键的列名）
        factory.setGeneratedKeysColumnNames("id");

        // 创建 PreparedStatementCreator
        PreparedStatementCreator psc = factory.newPreparedStatementCreator(params);

        // 创建 KeyHolder 以保存生成的键
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 执行插入操作
        int rowsAffected = jdbcTemplate.update(psc, keyHolder);

        // 检查插入是否成功
        if (rowsAffected > 0) {
            // 获取生成的ID
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

            // 在这里处理文件上传
            if (img != null && !img.isEmpty()) {
                try {
                    // 构建文件保存路径
                    Path filePath = Paths.get(uploadDir, "product-" + generatedId + ".jpg");
                    // 保存文件到指定路径
                    img.transferTo(filePath);

                    // 插入成功且文件保存成功，返回 true
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 插入失败或文件保存失败，返回 false
        return false;
    }

    @Override
    public boolean updateItem(Integer id, String name, int price, String describe, int categoryId, int quantity){
        String sql = "UPDATE `item` SET `name` = ?, `price` = ?, `describe` = ?, " +
                "`category_id` = ?, `quantity` = ? WHERE `id` = ?";
        // 使用 JdbcTemplate 的 update 方法執行 SQL 語句
        int rowsAffected = jdbcTemplate.update(sql, name, price, describe, categoryId, quantity, id);
        return (rowsAffected > 0);
    }
}
