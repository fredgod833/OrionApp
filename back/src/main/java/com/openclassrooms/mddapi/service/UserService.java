package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {
    User getUserById(int id_user);
    User subscribe(int id_user, int id_subject);
    User getByEmail(String email);
    User unsubscribe(int id_user, int id_subject);
    User deleteUserAccount(int id_user);
}
