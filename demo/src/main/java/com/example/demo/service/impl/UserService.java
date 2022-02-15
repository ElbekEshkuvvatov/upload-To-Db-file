package com.example.demo.service.impl;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user );
    }

    public Boolean checkUserName(String userName){
        return userRepository.existsByUsername(userName);
    }

    public Response getAllUser(){
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty())
            return new Response("The database is empty", false);

        return new Response("Success!", true, userList);
    }

    public Response getUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new Response("Success!", true, user)).orElseGet(() -> new Response("Such user id was not found!", false));
    }

    public Response deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.delete(userRepository.getById(id));
            return new Response("User deleted!", true);
        } else {
            return new Response("such user id was not found! ", false);
        }
    }

    public  Response editUser(Integer id, User user ){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Response("such user id was not found", false);

        User user1 = optionalUser.get();
        user1.setPassword(user.getPassword());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());

        userRepository.save(user1);

        return new Response("User updated!", true);
    }
    
}
