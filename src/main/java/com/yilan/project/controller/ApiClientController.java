package com.yilan.project.controller;

import com.google.gson.Gson;
import com.yilan.project.dto.OrderRequest;
import com.yilan.project.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client/api")
@CrossOrigin(origins = "*")
public class ApiClientController {
    private final Gson gson = new Gson();
    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/a", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJson(){
        return """
                {
                    a: 50
                }
                """;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){
        return gson.toJson(clientService.authenticate(username, password));
    }
    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signUp(@RequestParam("username") String username, @RequestParam("password") String password,
                          @RequestParam("phoneNumber") String phoneNumber){
        return clientService.signUp(username, password, phoneNumber) ? "OK" : "FAIL";
    }
    @RequestMapping(value = "/getItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getItem(@RequestParam("id") Integer id){
        return gson.toJson(clientService.getItemById(id));
    }

    @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleData(@RequestBody OrderRequest orderRequest){
        return clientService.payOrder(orderRequest);
    }
}
