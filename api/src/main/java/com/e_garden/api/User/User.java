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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public User() {
        this.name = "";
        this.surname = "";
        this.groupNumber = -1;
        this.className = "";
    }

    public User(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", className='" + className + '\'' +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
