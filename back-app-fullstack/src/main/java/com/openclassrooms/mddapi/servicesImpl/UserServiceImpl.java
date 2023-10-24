package com.openclassrooms.mddapi.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.UserEntityDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.repositories.UserIRepository;
import com.openclassrooms.mddapi.services.UserIService;

@Service
public class UserServiceImpl implements UserIService {

    @Autowired
    private UserIRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // public boolean existsByEmail(String email) {
    // return userRepository.existsByEmail(email);
    // }

    @Override
    public UserEntityDto createUser(String name, String email, String password, List<Topic> topics) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        // user.setTopics(topics);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserEntity userSaved = userRepository.save(user);
        UserEntityDto userDto = modelMapper.map(userSaved, UserEntityDto.class);
        return userDto;
    }

    @Override
    public UserEntityDto login(String email, String password) {
        UserEntity user = userRepository.findByEmailAndPassword(email, password);

        UserEntityDto userDto = modelMapper.map(user, UserEntityDto.class);
        return userDto;
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntityDto updateUser(Long id, String name, String email, String password, List<Topic> topics) {
        UserEntity user = userRepository.findById(id).get();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        // user.setTopics(topics);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserEntity userUpdated = userRepository.save(user);
        UserEntityDto userDto = modelMapper.map(userUpdated, UserEntityDto.class);
        return userDto;
    }

    // @Override
    // public void subscribe(UserEntity user, Topic topic) {
    // UserEntity currentUser = findById(user.getId());

    // boolean alreadyParticipate = currentUser.getTopics().stream().anyMatch(t ->
    // t.getId().equals(currentUser));
    // if (alreadyParticipate) {
    // throw new RuntimeException("You are already subscribed to this topic");
    // }

    // currentUser.getTopics().add(topic);
    // userRepository.save(currentUser);
    // }

    // @Override
    // public void unsubscribe(UserEntity user, Topic topic) {
    // UserEntity currentUser = findById(user.getId());

    // boolean alreadyParticipate = currentUser.getTopics().stream().anyMatch(t ->
    // t.getId().equals(currentUser));
    // if (!alreadyParticipate) {
    // throw new RuntimeException("You are not subscribed to this topic");
    // }

    // currentUser.getTopics().remove(topic);
    // userRepository.save(currentUser);
    // }

}
