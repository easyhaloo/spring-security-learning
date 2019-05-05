package com.nick.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author haloo
 * @Date 2019/5/3 0:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/username")
    public String getUserName(){
        return "haloo";
    }
}
