package com.example.demo.controller;



import com.example.demo.entity.User;
import com.example.demo.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity<>("Parol uzunligi 4 dan kam", HttpStatus.BAD_REQUEST);
        }

        if (userService.checkUserName(user.getUsername())) {
            return new ResponseEntity<>("Bu user oldin ro'yxatdan o'tgan", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.create(user));
    }

    private boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }



}
