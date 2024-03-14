package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.dtos.RegisterDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final ThemeService themeService;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepo,
            ModelMapper modelMapper,
            ThemeService themeService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.themeService = themeService;
    }

    public void register(RegisterDTO registerDTO) {
        User user = modelMapper.map(registerDTO, User.class);

        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepo.save(user);
    }

    public User getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + id + " id not found"));
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " email not found"));
    }

    @Override
    public User updateUser(int id, UserDTO userDTO) {
        User user = getUserById(id);

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        return userRepo.save(user);
    }

    public boolean userExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public User subscribeToTheme(int id, String username) {
        return updateSubscriptions(id, username, true);
    }

    public User unsubscribeFromTheme(int id, String username) {
        return updateSubscriptions(id, username, false);
    }

    private User updateSubscriptions(int themeId, String email, boolean request) {
        Theme theme = themeService.getById(themeId);
        User user = getByEmail(email);

        if (request) {
            if (!user.getSubscriptions().contains(theme)) {
                user.getSubscriptions().add(theme);
            } else {
                throw new RuntimeException("Subscription already exists");
            }
        } else if (user.getSubscriptions().contains(theme)) {
            user.getSubscriptions().removeIf(subscription -> subscription.equals(theme));
        } else {
            throw new RuntimeException("Not subscribed to theme yet");
        }

        return userRepo.save(user);
    }
}
