package com.e_garden.api.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Id
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
    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, name = "roles")
    private String role;

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

    @Setter
    @Getter
    @Column(nullable = true, length = 2048)
    private String password;

    @Setter
    @Getter
    @Column(nullable = true, length = 255, name = "class_name")
    private String className;

    @Setter
    @Getter
    @Column(nullable = true, name ="class_number")
    private Integer groupNumber;

    public String getRole() {
        return String.valueOf(role);
    }

    public void setRole(String role) {
        this.role = role;
    }

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
}
