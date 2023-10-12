package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long id;
	
	@NotNull
	private Date date;
	
	@NotNull
	private String content;
	
	private Post post;
	
	private User user;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;

}
