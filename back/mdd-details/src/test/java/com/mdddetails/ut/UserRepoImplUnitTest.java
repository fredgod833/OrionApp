package com.mdddetails.ut;

import com.mddcore.domain.models.User;
import com.mdddetails.mapper.UserDetailsMapper;
import com.mdddetails.models.UserEntity;
import com.mdddetails.repository.user.UserJpaRepository;
import com.mdddetails.repository.user.UserRepoImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("UserRepoImpl_Unit_Test")
public class UserRepoImplUnitTest {

    @InjectMocks
    private UserRepoImpl userRepo;
    @Mock
    private UserJpaRepository jpaRepository;
    @Mock
    private UserDetailsMapper userDetailsMapper;


    @Test
    public void findById_ShouldReturnUser_WhenUserExist() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@gmail.com");
        User user = new User();
        user.setEmail("test@gmail.com");

        when(jpaRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userDetailsMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepo.findById(id);

        assertThat(result.get().getEmail()).isEqualTo(userEntity.getEmail());
        verify(jpaRepository).findById(id);
        verify(userDetailsMapper).toDomain(userEntity);
    }

    @Test
    public void save_ShouldSaveUser_WithValidEntry() {
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userDetailsMapper.toEntity(user)).thenReturn(userEntity);

        userRepo.save(user);

        verify(jpaRepository).save(userEntity);
        verify(userDetailsMapper).toEntity(user);
    }

    @Test
    public void delete_ShouldDeleteUser_WhenUserExists() {
        User user = new User();
        UserEntity userEntity = new UserEntity();

        when(userDetailsMapper.toEntity(user)).thenReturn(userEntity);

        userRepo.delete(user);


        verify(jpaRepository).delete(userEntity);
        verify(userDetailsMapper).toEntity(user);
    }

    @Test
    public void existByEmail_ShouldReturnTrue_WhenEmailExists() {
        String email = "test@gmail.com";

        when(jpaRepository.existsByEmail(email)).thenReturn(true);

        Boolean exist = userRepo.existByEmail(email);

        assertThat(exist).isTrue();
        verify(jpaRepository).existsByEmail(email);
    }

    @Test
    public void FindByEmail_ShouldUser_WhenUserExists() {
        String email = "test@gmail.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@gmail.com");
        User user = new User();
        user.setEmail("test@gmail.com");

        when(jpaRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userDetailsMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepo.findByEmail(email);

        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        verify(jpaRepository).findByEmail(email);
        verify(userDetailsMapper).toDomain(userEntity);
    }
}

