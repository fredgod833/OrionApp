package com.openclassrooms.mddapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;
}
