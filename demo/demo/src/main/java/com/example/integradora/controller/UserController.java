package com.example.integradora.controller;

import com.example.integradora.model.User;
import com.example.integradora.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final LibraryService libraryService;

    public UserController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return libraryService.addUser(user);
    }

    @GetMapping
    public Object getAllUsers() {
        return libraryService.getAllUsers(); 
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return libraryService.getUserById(id);
    }
}
