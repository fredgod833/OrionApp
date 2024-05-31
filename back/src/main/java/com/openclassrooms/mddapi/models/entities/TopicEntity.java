package com.openclassrooms.mddapi.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "TOPIC")
@RequiredArgsConstructor
@NoArgsConstructor
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @NonNull
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Size(max = 400)
    @NotNull
    @NonNull
    @Column(name = "description", nullable = false, length = 400)
    private String description;

}