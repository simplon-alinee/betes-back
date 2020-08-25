package com.example.betes.controller;

import com.example.betes.model.User;
import com.example.betes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/all")
    public Page<User> findAllUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "username") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection) {
        return userService.findAllUsers(page, size, sortProperty, sortDirection);
    }
}
