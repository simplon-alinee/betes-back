package com.example.betes.controller;

import com.example.betes.model.User;
import com.example.betes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String sayHello(){
        return "Hello";
    }


    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        System.out.println(userService.getById(1l));
        return userService.getById(id);
    }
}
