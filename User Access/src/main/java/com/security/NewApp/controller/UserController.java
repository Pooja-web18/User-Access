package com.security.NewApp.controller;

import com.security.NewApp.repository.UserRepository;
import com.security.NewApp.security.jwt.JwtGeneratorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.security.NewApp.service.UserService;
import com.security.NewApp.model.User;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGeneratorFilter jwtGeneratorFilter;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully: " + registeredUser.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName());
            return ResponseEntity.ok("Login successful for user: " + user.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }


    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to the User Access");
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUserDetails(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers(); // Assume this method exists
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/superadmin")
    public ResponseEntity<String> superAdminEndpoint() {
        return ResponseEntity.ok("Super Admin access granted.");
    }


}
