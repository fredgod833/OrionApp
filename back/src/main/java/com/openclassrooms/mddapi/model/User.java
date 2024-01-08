package com.openclassrooms.mddapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

/**
 * Stock user
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id_user;
    private String username;
    private String email;
    private String password;
    @OneToOne
    Subscription subscription;}
