package com.mddinfrastructure.http;

import com.mddcore.usecases.dto.UserDto;
import com.mddcore.usecases.iservice.IUserService;
import com.mddinfrastructure.mapper.UserUpdateMapper;
import com.mddinfrastructure.request.UserUpdateDto;
import com.mddinfrastructure.security.jwt.JwtExecImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final UserUpdateMapper userUpdateMapper;
    private final JwtExecImpl jwtExec;

    public UserController(IUserService userService, UserUpdateMapper userUpdateMapper, JwtExecImpl jwtExec) {
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
