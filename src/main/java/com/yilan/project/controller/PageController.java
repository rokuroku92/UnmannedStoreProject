package com.yilan.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping(value = "/login")
    public String pageLogin() {
        return "login";
    }
    @GetMapping(value = "/signup")
    public String pageSignUp() {
        return "signup";
    }
    @GetMapping(value = "/")
    public String pageIndex() {
        return "services";
    }
    @GetMapping(value = "/product")
    public String pageProduct() {
        return "product";
    }
    @GetMapping(value = "/services")
    public String pageService() {
        return "services";
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

