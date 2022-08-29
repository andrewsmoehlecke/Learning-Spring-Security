package com.project.springsecurity.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.project.springsecurity.entities.dtos.UserFullDto;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    public User() {
    }

    public User(UserFullDto dto) {
        this.id = dto.getId();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
    }

    public enum Roles {
        ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

        private final String role;

        Roles(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    public List<String> getRolesString() {
        return roles.stream().map(Roles::toString).collect(Collectors.toList());
    }

    public boolean hasRole(Roles r) {
        return roles.contains(r);
    }

    public void addRole(Roles r) {
        roles.add(r);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", enabled=" + enabled + ", roles=" + roles + "]";
    }
}
