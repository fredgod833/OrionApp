package com.openclassrooms.mddapi.models.payload.response;

/**
 * Format de message d'echange Rest pour l'obtention d'un message utilisateur
 */
public class MessageResponse {
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
