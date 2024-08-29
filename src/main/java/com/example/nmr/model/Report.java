package com.example.nmr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    int reportID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String name;

    LocalDateTime createdAt;

    public Report(User user, String name, LocalDateTime createdAt) {
        this.user = user;
        this.name = name;
        this.createdAt = createdAt;
    }
}
