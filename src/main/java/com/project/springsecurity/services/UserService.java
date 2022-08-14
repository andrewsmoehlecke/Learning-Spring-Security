package com.project.springsecurity.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.springsecurity.entities.User;
import com.project.springsecurity.exceptions.UserAlreadyExistsException;
import com.project.springsecurity.exceptions.UserNotFoundOnDatabaseException;
import com.project.springsecurity.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void save(User user) {
        if (!findByUsername(user.getUsername()).isEmpty()) {
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");
        }

        // Encode user password with BCrypy
        user.setPassword(passwordEncoder().encode(user.getPassword()));

        // Set default role
        user.addRole(User.Roles.ROLE_USER);

        userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Add admin role to user by Id
    public User addAdminRole(Long id) {
        Optional<User> user = findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundOnDatabaseException("User not found on database");
        }

        user.get().addRole(User.Roles.ROLE_ADMIN);
        return userRepository.save(user.get());
    }
}
