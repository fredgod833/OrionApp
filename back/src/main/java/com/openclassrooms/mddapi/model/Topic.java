package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
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

    @OneToMany(mappedBy = "topic")
    @JsonIgnore
    private Set<Post> topicPosts;

    @OneToMany(mappedBy = "topic")
    @JsonIgnore
    private Set<Subscription> topicSubscriptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Integer getUser_id() {
        return user.getId();
    }
}
