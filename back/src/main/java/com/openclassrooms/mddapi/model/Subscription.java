package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_subscription;
    @ManyToOne
    @JsonBackReference
    private Feed feed;
    @OneToMany(mappedBy = "subscription")
    @JsonManagedReference
    private List<Subject> subjectList = new ArrayList<>();
}
