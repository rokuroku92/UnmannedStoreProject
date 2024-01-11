package com.yilan.project.controller;

import com.google.gson.Gson;
import com.yilan.project.dto.PutItemRequest;
import com.yilan.project.dto.UpdateItemRequest;
import com.yilan.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/admin/api")
@CrossOrigin(origins = "*")
public class ApiAdminController {
    @Autowired
    private AdminService adminService;
    private final Gson gson = new Gson();

    @GetMapping(value = "/getAllItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllItem(){
        return gson.toJson(adminService.getAllItem());
    }

    @GetMapping(value = "/getAllOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllOrder(){
        return gson.toJson(adminService.getAllOrders());
    }

    @PostMapping(value = "/putItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String putItem(@ModelAttribute PutItemRequest putItemRequest,
                          @ModelAttribute("img") MultipartFile img){
        // 示例输出
        System.out.println("Name: " + putItemRequest.getName());
        System.out.println("Price: " + putItemRequest.getPrice());
        System.out.println("Description: " + putItemRequest.getDescribe());
        System.out.println("Category ID: " + putItemRequest.getCategoryId());
        System.out.println("Quantity: " + putItemRequest.getQuantity());

        return adminService.putItem(putItemRequest, img);
    }
    @PostMapping(value = "/updateItem", produces = MediaType.TEXT_PLAIN_VALUE)
    public String updateItem(@RequestBody UpdateItemRequest updateItemRequest){
        return adminService.updateItem(updateItemRequest);
    }


}
