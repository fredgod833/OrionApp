package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class InternalUserDTO {
    private Long id;
    
    @NonNull
    private String name;
    
    @NonNull
	@Email
    private String email;
    
    @NonNull
	@Size(min = 8)
    private String password;
    
    private LocalDateTime created_at;
    
    private LocalDateTime updated_at;
}
