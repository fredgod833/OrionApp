//package com.mddinfrastructure.repository;
//
//import com.mddcore.domain.models.User;
//import com.mddcore.domain.repository.IUserRepository;
//import com.mddinfrastructure.mapper.UserEntityMapper;
//import com.mddinfrastructure.request.UserEntityDto;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Component
//public class UserRepositoryImpl implements IUserRepository {
//
//    private final UserRepoApi userRepoApi;
//    private final UserEntityMapper userEntityMapper;
//
//    public UserRepositoryImpl(UserRepoApi userRepoApi, UserEntityMapper userEntityMapper) {
//        this.userRepoApi = userRepoApi;
//        this.userEntityMapper = userEntityMapper;
//    }
//
//    @Override
//    public Boolean existByEmail(String email) {
//        return userRepoApi.existByEmail(email);
//    }
//
//    @Override
//    public Optional<User> findById(Long id) {
//        return userRepoApi.findById(id).map(userEntityMapper::toEntity);
//    }
//
//    @Override
//    public List<User> findAll() {
//        return userRepoApi.findAll().stream()
//                .map(userEntityMapper::toEntity)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public User save(User entity) {
//        UserEntityDto userEntityDto = userEntityMapper.toDto(entity);
//        userRepoApi.save(userEntityDto);
//        return entity;
//    }
//
//    public void delete(User entity) {
//        UserEntityDto userEntityDto = userEntityMapper.toDto(entity);
//        userRepoApi.delete(userEntityDto);
//    }
//}
