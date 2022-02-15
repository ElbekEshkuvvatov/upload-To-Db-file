package com.example.demo.controller;


import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.impl.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.web.bind.annotation.*;



import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public Response getAllUser() {

        return userService.getAllUser();
    }

    @GetMapping("/getId/{id}")
    public Response getOneUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/put/{id}")
    public Response editUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.editUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }


}
