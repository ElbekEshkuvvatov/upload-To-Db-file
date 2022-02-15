package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.entity.VM.Login;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserJWTController {

  private  final   AuthenticationManager authenticationManager;

  private  final JWTTokenProvider jwtTokenProvider;

  private final UserRepository userRepository;
    public UserJWTController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Login login){


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        User user = userRepository.findByUsername(login.getUsername());
        if (user ==null){
            throw new UsernameNotFoundException("Bu foydalanuvchi mavjud emas");
        }
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        Map<Object, Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("token", token);
        return ResponseEntity.ok(map);

      //  https://jwt.io/  Encode ni decode qilib beradigan sayt

    }


}
