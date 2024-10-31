package com.yashpz.journel.journel.controller;

import com.yashpz.journel.journel.entity.User;
import com.yashpz.journel.journel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();

        if (allUsers!=null && !allUsers.isEmpty())
            return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);

        return new ResponseEntity<List<User>>( HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-admin-user")
    public User createAdmin(@RequestBody User user){
        return userService.createAdminUser(user);
    }
}
