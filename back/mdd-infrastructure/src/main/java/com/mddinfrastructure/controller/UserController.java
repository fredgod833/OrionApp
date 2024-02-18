package controller;

import com.openclassrooms.mddapi.core.usecases.user.IUserService;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.request.UserUpdateDto;
import com.openclassrooms.mddapi.infrastructure.mapper.UserUpdateMapper;
import com.openclassrooms.mddapi.infrastructure.security.IJwtExec;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final UserUpdateMapper userUpdateMapper;
    private final IJwtExec jwtExec;

    public UserController(IUserService userService, UserUpdateMapper userUpdateMapper, IJwtExec jwtExec) {
        this.userService = userService;
        this.userUpdateMapper = userUpdateMapper;
        this.jwtExec = jwtExec;
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUser(@PathVariable("id")Long id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteUser(@PathVariable("id")Long id) {
        String authenticatedEmail = jwtExec.getAuthenticateUser();
        userService.delete(id, authenticatedEmail);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateUser(@PathVariable("id")Long id, UserUpdateDto userDto) {
        UserDto user = userUpdateMapper.toDto(userDto);
        userService.update(id, user);
        return ResponseEntity.ok().build();
    }
}
