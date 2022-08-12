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
import javax.persistence.Id;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    public enum Roles {
        ROLE_ADMIN("ADMIN"), ROLE_DEFAULT("DEFAULT");

        private final String role;

        Roles(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public List<String> getRolesString() {
        return roles.stream().map(Roles::toString).collect(Collectors.toList());
    }

    public void setRoles(Set<Roles> r) {
        roles = r;
    }

    public boolean hasRole(Roles r) {
        return roles.contains(r);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", password=" + password + ", roles=" + roles + ", username=" + username + "]";
    }
}
