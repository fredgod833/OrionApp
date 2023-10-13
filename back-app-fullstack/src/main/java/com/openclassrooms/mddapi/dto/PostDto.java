package com.openclassrooms.mddapi.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
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
	private String title;

	@NotBlank
	private UserEntity User;

	@NotBlank
	private Date Date;

	@NotBlank
	private String content;

	@NotBlank
	private Topic topic;

	private List<Comment> comments;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;
}
