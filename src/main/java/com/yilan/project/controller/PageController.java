package com.yilan.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping(value = "/login")
    public String pageSignIn() {
        return "login";
    }
    @GetMapping(value = "/")
    public String pageIndex() {
        return "index";
    }
    @GetMapping(value = "/product")
    public String pageProduct() {
        return "product";
    }
    @GetMapping(value = "/service")
    public String pageService() {
        return "service";
    }
    @GetMapping(value = "/about")
    public String pageAbout() {
        return "about";
    }
    @GetMapping(value = "/contact")
    public String pageContact() {
        return "contact";
    }
    @GetMapping(value = "/management")
    public String pageManagement() {
        return "management";
    }

}

