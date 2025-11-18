package com.openclassrooms.mddapi.models.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Format de message d'echange Rest pour les requetes d'enregistrement utilisateur
 */
@Data
public class SignupRequest {

  @NotBlank
  @Size(max = 50, message="l'email ne doit pas dépasser 50 caratères.")
  @Email
  private String email;

  @NotBlank
  @Size(min = 3, max = 20, message="le nom doit avoir entre 3 et 20 caratères.")
  private String login;

  @NotBlank
  @Size(min = 8, max = 40, message="le mot de passe doit avoir entre 8 et 40 caratères.")
  private String password;

}
