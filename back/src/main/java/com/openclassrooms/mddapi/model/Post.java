package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Comment> postComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Integer getUser_id() {
        return user.getId();
    }

    public Integer getTopic_id() {
        return topic.getId();
    }

    public String getUser_name() {
        return user.getName();
    }
}
