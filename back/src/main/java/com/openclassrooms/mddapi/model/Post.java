package com.openclassrooms.mddapi.model;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Builder;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
/**
 * Stock post
 */
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_post;
    private String title;
    private LocalDateTime date;
    private String author;
    private String content;
    private String comments;
}
