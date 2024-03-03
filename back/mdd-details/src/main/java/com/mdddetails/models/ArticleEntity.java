package com.mdddetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
    private String title;
    private String content;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentsList = new ArrayList<>();

//    public static ArticleEntity from(Article article) {
//        return new ArticleEntity(
//                article.getId(),
//                article.getTitle(),
//                article.getContent(),
//                null,
//                SubjectMapper.INSTANCE.toEntity(article.getSubject()),
//                UserMapper.INSTANCE.toEntity(article.getUser()),
//                CommentMapper.INSTANCE.toEntity(article.getCommentsList())
//        );
//    }
//
//    public static List<ArticleEntity> from(List<Article> articleList) {
//        return articleList.stream().map(ArticleEntity::from
//        ).collect(Collectors.toList());
//    }
//
//    public static Article to(ArticleEntity article) {
//        return new Article(
//                article.getId(),
//                SubjectMapper.INSTANCE.toDomain(article.getSubject()),
//                article.getTitle(),
//                article.getContent(),
//                null,
//                UserMapper.INSTANCE.toDomain(article.getUser()),
//                CommentMapper.INSTANCE.toDomain(article.getCommentsList())
//        );
//    }
//
//    public static List<Article> to(List<ArticleEntity> articleList) {
//        return articleList.stream().map(ArticleEntity::to
//        ).collect(Collectors.toList());
//    }
}
