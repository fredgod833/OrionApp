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
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_feed;
    @OneToOne
    @JsonBackReference
    private User user;
    @OneToMany(mappedBy = "feed",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Subscription> subscriptionList = new ArrayList<>();
}
