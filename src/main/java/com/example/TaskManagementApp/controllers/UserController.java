package com.example.TaskManagementApp.controllers;

import com.example.TaskManagementApp.Service.UserService;
import com.example.TaskManagementApp.entity.User;
import com.example.TaskManagementApp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Integer userId, @RequestBody String newPassword) {
        try {
            userService.changePassword(userId, newPassword);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String email) {
        try {
            userService.resetPassword(email);
            return new ResponseEntity<>("Password reset successfully. Check your email for the new password.", HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}