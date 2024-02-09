package com.openclassrooms.mddapi.details.repository;

import com.tonight.back.details.mapper.UserMapperDetails;
import com.tonight.back.details.models.UserEntity;
import com.tonight.back.domain.user.IUserRepository;
import com.tonight.back.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private UserJpaRepository jpaUserRepository;

    @Autowired
    private UserMapperDetails userMapperDetails;

    public UserRepositoryImpl(UserJpaRepository jpaUserRepository, UserMapperDetails userMapperDetails) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapperDetails = userMapperDetails;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userMapperDetails::toDto);
    }

    @Override
    public Boolean existByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(userMapperDetails::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userMapperDetails::toDto);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return userMapperDetails.toDto(savedEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        jpaUserRepository.delete(userEntity);
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        UserEntity updatedEntity = jpaUserRepository.save(userEntity);
        return userMapperDetails.toDto(updatedEntity);
    }

    @Override
    public Boolean exist(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public Boolean isAdmin(Long id) {
        return jpaUserRepository.isAdmin(id);
    }
}
