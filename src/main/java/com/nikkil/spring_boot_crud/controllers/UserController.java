package com.nikkil.spring_boot_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikkil.spring_boot_crud.entity.UserEntity;
import com.nikkil.spring_boot_crud.exceptions.NotFoundException;
import com.nikkil.spring_boot_crud.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository; // UserRepository has all the CRUD methods required

    // Getting all users
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
    // Getting only one user by id
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id: " + id));
    }
    // Creating a new user
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }
    // Updating an existing user
    @PostMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        var foundUser =  getUserById(id);
        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        return userRepository.save(foundUser);
    }
    // Deleting a user
    @DeleteMapping("/{id}")
    public List<UserEntity> deleteUser(@PathVariable Long id) {
        var foundUser =  getUserById(id);
        userRepository.delete(foundUser);  
        return getUsers();
    }

    
}
