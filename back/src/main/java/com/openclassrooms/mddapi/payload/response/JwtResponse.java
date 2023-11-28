package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String name;

  private Long authLevel;

  public JwtResponse(String accessToken, Long id, String username, String name, Long authLevel) {
    this.token = accessToken;
    this.id = id;
    this.name = name;
    this.username = username;
    this.authLevel = authLevel;
  }
}
