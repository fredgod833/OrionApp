package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;


@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    private String username;
    private String lastname;
    private String email;
    private String password;
    @OneToOne(mappedBy = "user",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    @JsonManagedReference
    private Feed feed;
}
