package com.yilan.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/agv")
public class PageController {

    @GetMapping(value = "/")
    public String pageSignIn() {
        return "signIn";
    }

}

