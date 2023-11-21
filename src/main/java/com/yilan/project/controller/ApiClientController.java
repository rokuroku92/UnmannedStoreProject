package com.yilan.project.controller;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client/api")
@CrossOrigin(origins = "*")
public class ApiClientController {
    private final Gson gson = new Gson();

    @GetMapping(value = "/a", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJson(){
        return """
                {
                    a: 50
                }
                """;
    }

    @RequestMapping(value = "/b", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJsonByA(@RequestParam("value") String a){
        return """
                {
                    a:\040""" + a + """
                }
                """;
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleData(@RequestBody String data){  // Any Object
        return """
                {
                    a:\040""" + data + """
                }
                """;
    }
}
