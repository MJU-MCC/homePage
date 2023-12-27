package com.example.mccHomePage.controller;

import com.example.mccHomePage.Dto.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mcc")
public class RestApiController {

    @PostMapping("/login")
    public User login(@RequestBody User user){
        String id = user.getId();
        String password = user.getPassword();

        User user1 = new User(id, password);

        return user1;
    }
}
