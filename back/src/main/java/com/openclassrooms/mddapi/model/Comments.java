package com.openclassrooms.mddapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
    List of comments
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Comments {
    @Id
    @GeneratedValue
    private int id_comments;
    private String comment;
}
