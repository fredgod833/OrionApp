package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "themes", schema = "mdd")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotEmpty
    @Size(max = 60)
    @Column(unique = true)
    String name;

    @NotEmpty
    @Size(max = 2500)
    String description;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
}
