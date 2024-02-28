package com.mdddetails.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ArticleEntity> articleList = new ArrayList<>();
}

