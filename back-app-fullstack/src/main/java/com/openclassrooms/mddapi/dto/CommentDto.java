package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.openclassrooms.mddapi.models.UserEntity;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long id;

	@NotNull
	private Date date;

	@NotBlank
	private String content;

	@NotBlank
	private UserEntity user;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;

}
