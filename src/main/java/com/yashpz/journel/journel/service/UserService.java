package com.yashpz.journel.journel.service;

import com.yashpz.journel.journel.entity.JournalEntry;
import com.yashpz.journel.journel.entity.User;
import com.yashpz.journel.journel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers (){
        return userRepository.findAll();
    }

    public User createUser(User newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(List.of("USER"));
        return saveUser(newUser);
    }

    public User createAdminUser(User newUser){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(List.of("USER","ADMIN"));
        return saveUser(newUser);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserByUsername(username);
    }

    public User updateUser(User newUser){
        User oldUser = getCurrentUser();
        if(oldUser!=null){
            oldUser.setUsername(newUser.getUsername());
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            saveUser(oldUser);
        }
        return oldUser;
    }

    public void deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(username);
    }
}
