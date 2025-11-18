package com.openclassrooms.mddapi.models.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Format de message d'echange Rest pour l'obtention d'un token JWT
 */
@Getter
@Setter
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private int id;
  private String username;
  private String email;

  private Boolean admin;

  public JwtResponse(String accessToken, int id, String username, String email, Boolean admin) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.admin = admin;
  }
}
