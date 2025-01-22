package com.e_garden.api.user;

import com.e_garden.api.event.Event;
import com.e_garden.api.report.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Report> reports = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Event> events = new ArrayList<>();
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    @Column(nullable = true, length = 255)
    private String name;
    @Setter
    @Getter
    @Column(nullable = true, length = 255)
    private String surname;
    @Setter
    @Getter
    @Column(nullable = false, length = 255, unique = true)
    private String email;
    @Setter
    @Getter
    @Column(nullable = false, name = "roles")
    private String role;
    @JsonIgnore
    @Column(nullable = true, length = 2048)
    private String password;
    @Column(nullable = true, length = 255, name = "class_name")
    private String className;
    @Column(nullable = true, name = "class_number")
    private Integer groupNumber;

    @JsonIgnore
    @Column(nullable = false, name = "enable")
    private boolean enable;

    @JsonIgnore
    @Column(nullable = false, name = "locked")
    private boolean locked;

    public User() {
        this.locked = false;
        this.enable = true;
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

    public User(String email, String password, String name, String surname, String className) {
        this(email, password);
        this.name = name;
        this.surname = surname;
        this.className = className;

    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isLocked() {
        return !locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Report> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", className='" + className + '\'' +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
