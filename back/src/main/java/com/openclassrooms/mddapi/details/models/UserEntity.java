package com.openclassrooms.mddapi.details.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @NotNull
    @Email
    private String username;
    @NotNull
    @Size(min = 3, max = 50)
    private String firstname;
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;
    @Size(min = 6, max = 80)
    private String password;
    private Date birthday;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteEntity> favorites;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParticipationEntity> participation;
    private Boolean isAdmin;
}
