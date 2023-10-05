package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> user_id(int id_user);

    User getByEmail(String email);
}
