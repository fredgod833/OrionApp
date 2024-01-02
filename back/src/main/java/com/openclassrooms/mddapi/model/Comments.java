package com.openclassrooms.mddapi.model;

import lombok.*;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_comments;
    private String comment;
    @Column(name = "id_post")
    private int post;
    private String author;

    public static Comments fromString(String comment){
        Comments comments = new Comments();
        comments.comment = comment;
        return comments;
    }
}
