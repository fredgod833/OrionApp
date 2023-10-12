package com.openclassrooms.mddapi.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	private String title;
	
	@NotNull
	@Size(max = 300)
	private String description;
	
	private List<Post> posts;
	
	private List<User> users;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
