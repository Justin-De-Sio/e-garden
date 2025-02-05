package com.e_garden.api.user;

import com.e_garden.api.event.Event;
import com.e_garden.api.report.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Le type User.
 */
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

    /**
     * Instancie un nouveau User.
     */
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

    /**
     * Instancie un nouveau User.
     *
     * @param email    l'email
     * @param password le password
     */
    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    /**
     * Instancie un nouveau User.
     *
     * @param email     l'email
     * @param password  le password
     * @param name      le name
     * @param surname   le surname
     * @param className la class name
     */
    public User(String email, String password, String name, String surname, String className) {
        this(email, password);
        this.name = name;
        this.surname = surname;
        this.className = className;
    }

    /**
     * Gets date last login.
     *
     * @return la date last login
     */
    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    /**
     * Sets date last login.
     *
     * @param dateLastLogin la date last login
     */
    public void setDateLastLogin(LocalDateTime dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }

    /**
     * Gets created at.
     *
     * @return le created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt le created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets nb login failure.
     *
     * @return le nb login failure
     */
    public Integer getNbLoginFailure() {
        return nbLoginFailure;
    }

    /**
     * Sets nb login failure.
     *
     * @param nbLoginFailure le nb login failure
     */
    public void setNbLoginFailure(Integer nbLoginFailure) {
        this.nbLoginFailure = nbLoginFailure;
    }

    /**
     * Is enable boolean.
     *
     * @return le boolean
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Sets enable.
     *
     * @param enable enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Is locked boolean.
     *
     * @return le boolean
     */
    public boolean isLocked() {
        return !locked;
    }

    /**
     * Sets locked.
     *
     * @param locked le locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Gets surname.
     *
     * @return le surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname le surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets name.
     *
     * @return le name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name le name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets class name.
     *
     * @return la class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets class name.
     *
     * @param className la class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets group number.
     *
     * @return le group number
     */
    public Integer getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets group number.
     *
     * @param groupNumber le group number
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Gets email.
     *
     * @return l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email l'email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return le password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password le password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return le role
     */
    public String getRole() {
        return String.valueOf(role);
    }

    /**
     * Sets role.
     *
     * @param role le role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets identifiant.
     *
     * @return l'identifiant
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets identifiant.
     *
     * @param id l'identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets events.
     *
     * @return l'events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Gets rapports.
     *
     * @return les rapports
     */
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


    /**
     * To string string.
     *
     * @return le string
     */
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
