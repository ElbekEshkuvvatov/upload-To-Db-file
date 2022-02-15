package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(Integer id);

    User findByUsername(String username);

    boolean existsByUsername(String userName);


    boolean existsById(Integer id);

    User getById(Integer id);
}
