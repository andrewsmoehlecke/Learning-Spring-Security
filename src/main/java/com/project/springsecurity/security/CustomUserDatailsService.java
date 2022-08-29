package com.project.springsecurity.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.springsecurity.entities.User;
import com.project.springsecurity.exceptions.UserNotFoundOnDatabaseException;
import com.project.springsecurity.repositories.UserRepository;

@Service
public class CustomUserDatailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomUserDatailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> existsUser = userRepository.findByUsername(username);

        if (existsUser.isEmpty()) {
            throw new UserNotFoundOnDatabaseException("Principal user not found on database");
        }

        return UserPrincipal.create(existsUser.get());
    }

}
