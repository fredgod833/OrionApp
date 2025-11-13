package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.InvalidRegistrationException;
import com.openclassrooms.mddapi.exceptions.InvalidUserException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.dto.UserDto;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Service de Gestion des utilisateurs
     * @param userRepository
     * @param userMapper
     */
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    /**
     * Recherche l'utilisateur par son id
     * @param id
     * @return
     * @throws InvalidUserException
     */
    public UserDto findById(Integer id) throws InvalidUserException {
        UserEntity user =  this.userRepository.findById(id).orElseThrow(() -> new InvalidUserException("utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    /**
     * Recherche si un utilisateur existe avec ce login ou cet email.
     * @param loginOrEmail : la chaine de caractère à rechercher.
     * @return true si un utilisateur existe avec ce login ou cet email.
     */
    public boolean checkExistingLoginOrEmail(final String loginOrEmail) {
        return userRepository.existsByLoginOrEmail(loginOrEmail, loginOrEmail);
    }

}
