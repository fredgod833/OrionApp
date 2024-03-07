package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.models.User;

import java.util.List;

public interface UserService {

    /**
     *
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);

    /**
     *
     * @return
     */
    List<User> getAll();

    /**
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
}
