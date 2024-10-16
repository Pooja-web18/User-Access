package com.security.NewApp.service.serviceimpl;

import com.security.NewApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.NewApp.repository.UserRepository;
import com.security.NewApp.service.UserService;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        } else if (user.getRole().equalsIgnoreCase("SUPER_ADMIN")) {
            user.setRole("ROLE_SUPER_ADMIN");
        } else if (user.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
