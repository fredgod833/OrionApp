package com.openclassrooms.mddapi.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "USERS", uniqueConstraints = {
	    @UniqueConstraint(columnNames = "email")
	})
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @NonNull
	  private String name;
	  
	  @NonNull
	  @Email
	  private String email;
	  
	  @NonNull
	  @Size(min = 8)
	  private String password;
	  
	  @OneToMany(mappedBy = "user")
	  private List<Comment> comments;
	  
	  @CreatedDate
	  @Column(name = "created_at", updatable = false)
	  private LocalDateTime createdAt;

	  @UpdateTimestamp
	  @Column(name = "updated_at")
	  private LocalDateTime updatedAt;
}
