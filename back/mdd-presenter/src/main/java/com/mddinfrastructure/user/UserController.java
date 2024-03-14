package com.mddinfrastructure.user;

import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.user.DeleteUserUseCase;
import com.mddcore.usecases.user.GetUserUseCase;
import com.mddcore.usecases.user.UpdateUserUseCase;
import com.mddinfrastructure.mapper.UserUpdateMapper;
import com.mddinfrastructure.request.UserSettingRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.UserResponse;
import com.mddinfrastructure.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@Component
public class  UserController implements UserResource {

    private final UseCaseExecutor useCaseExecutor;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UseCaseExecutor useCaseExecutor, GetUserUseCase getUserUseCase,
                          DeleteUserUseCase deleteUserUseCase, UpdateUserUseCase updateUserUseCase, JwtTokenProvider jwtTokenProvider) {
        this.useCaseExecutor = useCaseExecutor;
        this.getUserUseCase = getUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public CompletableFuture<UserResponse> getUserById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getUserUseCase,
                new GetUserUseCase.InputValues(id),
                (outputValues) -> UserResponse.from(outputValues.user()));
    }

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> deleteUserById(@PathVariable Long id) {
        Long authId = jwtTokenProvider.getAuthenticateUser();
        return useCaseExecutor.execute(
                deleteUserUseCase,
                new DeleteUserUseCase.InputValues(id, authId),
                outputValues -> {
                    if (outputValues.isDeleted()) {
                        return ResponseEntity.ok(new ApiResponse(true, "Delete user successfully"));
                    } else {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "A problem occurred while deleting user"));
                    }
                }
        );
    }

    @Override
    public CompletableFuture<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserSettingRequest userSettingRequest) {
        Long authId = jwtTokenProvider.getAuthenticateUser();
        return useCaseExecutor.execute(
                updateUserUseCase,
                new UpdateUserUseCase.InputValues(id, UserUpdateMapper.INSTANCE.toDomain(userSettingRequest), authId),
                outputValues -> UserResponse.from(outputValues.user()));
    }

    @Override
    public CompletableFuture<ResponseEntity<?>> getUserAuth() {
        Long authId =  jwtTokenProvider.getAuthenticateUser();
        ResponseEntity<Long> responseEntity = ResponseEntity.ok(authId);
        return CompletableFuture.completedFuture(responseEntity);
    }
}
