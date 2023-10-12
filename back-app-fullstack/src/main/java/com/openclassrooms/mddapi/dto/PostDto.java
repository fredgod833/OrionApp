package com.openclassrooms.mddapi.dto;

import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "title")
	private String title;
	
	@NotNull
	private String author;
	
	@NotNull
	private Date Date;
	
	@NotNull
	private String Content;
	
	@NotNull
	private Topic topic;
	
	private List<Comment> comments;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
