package com.mdddetails.repository.user;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mdddetails.mapper.UserDetailsMapper;
import com.mdddetails.models.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepoImpl implements IUserRepository {
    private final UserJpaRepository jpaUserRepository;

    private final UserDetailsMapper userDetailsMapper;

    public UserRepoImpl(UserJpaRepository jpaUserRepository, UserDetailsMapper userDetailsMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userDetailsMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userDetailsMapper.toEntity(user);
        jpaUserRepository.save(userEntity);
        return user;
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userDetailsMapper.toEntity(user);
        jpaUserRepository.delete(userEntity);
    }

    @Override
    public Boolean existByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
       return jpaUserRepository.findByEmail(email).map(userDetailsMapper::toDomain);
    }
}
