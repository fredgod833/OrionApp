package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.dto.UserEntityDto;

public interface UserIService {

    // public boolean existsByEmail(String email);

    public UserEntityDto createUser(String name, String email, String password, List<Topic> topics);

    public UserEntityDto login(String email, String password);

    public UserEntity findById(Long id);

    public UserEntityDto updateUser(Long id, String name, String email, String password, List<Topic> topics);

    // public void subscribe(UserEntity user, Topic topic);

    // public void unsubscribe(UserEntity user, Topic topic);

    // logout
}
