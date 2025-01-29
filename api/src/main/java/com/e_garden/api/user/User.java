package com.e_garden.api.user;

import com.e_garden.api.event.Event;
import com.e_garden.api.report.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, length = 255)
    private String name;
    @Column(nullable = true, length = 255)
    private String surname;
    @Column(nullable = false, length = 255, unique = true)
    private String email;
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

    @Column(nullable = false, name = "locked")
    private boolean locked;

    @JsonIgnore
    @Column(nullable = false, name = "nb_login_failure")
    private Integer nbLoginFailure;

    @JsonIgnore
    @Column(nullable = true, name = "date_last_login")
    private LocalDateTime dateLastLogin;

    @JsonIgnore
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    public User() {
        this.nbLoginFailure = 0;
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

    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(LocalDateTime dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getNbLoginFailure() {
        return nbLoginFailure;
    }

    public void setNbLoginFailure(Integer nbLoginFailure) {
        this.nbLoginFailure = nbLoginFailure;
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

    /**
     * Cette méthode est appelée automatiquement avant l'insertion dans la base de données.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
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
