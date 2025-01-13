package com.e_garden.api.User;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 255)
    private String name;
    @Column(nullable = true, length = 255)
    private String surname;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, name = "roles")
    private String role;
    @Column(nullable = true, length = 2048)
    private String password;
    @Column(nullable = true, length = 255, name = "class_name")
    private String className;
    @Column(nullable = true, name = "class_number")
    private Integer groupNumber;

    public User() {
        this.name = "";
        this.surname = "";
        this.groupNumber = -1;
        this.className = "";
        this.role = String.valueOf(Roles.UTILISATEUR);
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return String.valueOf(role);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
}
