package com.yashpz.journel.journel.controller;

import com.yashpz.journel.journel.entity.User;
import com.yashpz.journel.journel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
