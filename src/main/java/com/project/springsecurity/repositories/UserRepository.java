package com.project.springsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springsecurity.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
