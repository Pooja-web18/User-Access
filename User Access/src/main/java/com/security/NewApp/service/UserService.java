package com.security.NewApp.service;

import com.security.NewApp.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAllUsers();
}
