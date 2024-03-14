package com.mddcore.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Article {
    private Long id;
    private Subject subject;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private User user;
    private List<Comment> commentsList;
}
