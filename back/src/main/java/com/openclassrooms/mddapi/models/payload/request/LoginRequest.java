package com.openclassrooms.mddapi.models.payload.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Format de message d'echange Rest pour le login
 */
@Data
public class LoginRequest {

	@NotBlank
  	private String login;

	@NotBlank
	private String password;

}
