package com.example.nmr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    int userId;

    @NotBlank(message = "Name must not be empty")
    String name;

    @NotBlank(message = "Email must not be empty")
    @Email
    @Column(unique = true)
    String email;

    @NotBlank(message = "Phone Number must not be empty")
    String phoneNumber;

    @NotBlank(message = "Password must not be empty")
    String password;

    String role;

    @OneToMany(cascade = CascadeType.ALL)
    List<Result> results;

    @OneToMany(cascade = CascadeType.ALL)
    List<Report> reports;

    public User(String name, String email, String phoneNumber, String password, String role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString(){
        return name + "\n" + email + "\n" + phoneNumber + "\n" + role + "\n";
    }
}
