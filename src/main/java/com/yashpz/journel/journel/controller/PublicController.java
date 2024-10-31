package com.yashpz.journel.journel.controller;

import com.yashpz.journel.journel.entity.User;
import com.yashpz.journel.journel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("health")
    public ResponseEntity<String> checkHealth(){
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
}
