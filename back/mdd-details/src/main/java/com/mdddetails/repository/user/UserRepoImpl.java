package com.mdddetails.repository.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mdddetails.mapper.UserDetailsMapper;
import com.mdddetails.models.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Manages user data operations.
 */

@Repository
public class UserRepoImpl implements IUserRepository {
    private final UserJpaRepository jpaUserRepository;

    private final UserDetailsMapper userDetailsMapper;

    public UserRepoImpl(UserJpaRepository jpaUserRepository, UserDetailsMapper userDetailsMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    /**
     * Finds a user by ID.
     * @param id the user's ID
     * @return an {@link Optional} of {@link User}
     */
    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userDetailsMapper::toDomain);
    }

    /**
     * Saves a user, returning the saved user.
     * @param user the user to save
     * @return the saved user
     */
    @Override
    public User save(User user) {
        UserEntity userEntity = userDetailsMapper.toEntity(user);
        jpaUserRepository.save(userEntity);
        return user;
    }

    /**
     * Deletes a user.
     * @param user the user to delete
     */
    @Override
    public void delete(User user) {
        UserEntity userEntity = userDetailsMapper.toEntity(user);
        jpaUserRepository.delete(userEntity);
    }

    /**
     * Checks if an email exists in the database.
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    @Override
    public Boolean existByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    /**
     * Finds a user by email.
     * @param email the user's email
     * @return an {@link Optional} of {@link User}
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userDetailsMapper::toDomain);
    }
}
