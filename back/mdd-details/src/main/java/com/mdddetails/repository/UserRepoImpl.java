package com.mdddetails.repository;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mdddetails.mapper.UserMapperDetails;
import com.mdddetails.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepoImpl implements IUserRepository {
    private UserJpaRepository jpaUserRepository;

    @Autowired
    private final UserMapperDetails userMapperDetails;

    public UserRepoImpl(UserJpaRepository jpaUserRepository, UserMapperDetails userMapperDetails) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapperDetails = userMapperDetails;
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
        jpaUserRepository.save(userEntity);
        return user;
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userMapperDetails.toEntity(user);
        jpaUserRepository.delete(userEntity);
    }

    @Override
    public Boolean existByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
       return jpaUserRepository.findByEmail(email).map(userMapperDetails::toDto);
    }
}
