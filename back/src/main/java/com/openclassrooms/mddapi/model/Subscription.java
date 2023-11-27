package com.openclassrooms.mddapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_subscription;
    @OneToMany
    private List<Subject> subjectList = new ArrayList<>();
}
