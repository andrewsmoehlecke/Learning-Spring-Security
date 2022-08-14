package com.project.springsecurity.entities.dtos;

import java.util.List;

import com.project.springsecurity.entities.User;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserFullDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<String> roles;

    public UserFullDto() {
    }

    public UserFullDto(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.roles = u.getRolesString();
    }
}
