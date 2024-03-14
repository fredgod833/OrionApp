package com.mddcore.usecases.ut.user;

import com.mddcore.domain.models.User;
import com.mddcore.usecases.UseCaseExecutor;
import com.mddcore.usecases.user.DeleteUserUseCase;
import com.mddcore.usecases.user.GetUserUseCase;
import com.mddcore.usecases.user.UpdateUserUseCase;
import com.mddinfrastructure.request.UserSettingRequest;
import com.mddinfrastructure.response.ApiResponse;
import com.mddinfrastructure.response.SubscriptionResponse;
import com.mddinfrastructure.response.UserResponse;
import com.mddinfrastructure.security.jwt.JwtTokenProvider;
import com.mddinfrastructure.user.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UseCaseExecutor useCaseExecutor;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private DeleteUserUseCase deleteUserUseCase;
    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Test
    public void GetUserById_ShouldReturnUserResponse() {
        List<SubscriptionResponse> subscriptionList = new ArrayList<>();
        UserResponse userResponse = new UserResponse("test@gmail.com", "theo", subscriptionList);

        doReturn(CompletableFuture.completedFuture(userResponse)).when(useCaseExecutor).execute(any(), any(GetUserUseCase.InputValues.class), any());

        CompletableFuture<UserResponse> result = userController.getUserById(1L);

        assertThat(result.join().email()).isEqualTo(userResponse.email());
    }

    @Test
    public void DeleteUserById_ShouldReturnTrueInApiResponse() {
        Long id = 1L;
        ApiResponse apiResponse = new ApiResponse(true,"Delete user successfully");
        ResponseEntity<ApiResponse> expectedResponseEntity = ResponseEntity
                .ok(apiResponse);

        doReturn(CompletableFuture.completedFuture(expectedResponseEntity))
                .when(useCaseExecutor)
                .execute(eq(deleteUserUseCase), any(DeleteUserUseCase.InputValues.class), any());

        CompletableFuture<ResponseEntity<ApiResponse>> result = userController.deleteUserById(id);
        ResponseEntity<ApiResponse> response = result.join();

        assertThat(response.getStatusCode()).isEqualTo(expectedResponseEntity.getStatusCode());
        assertThat(response.getBody()).isEqualTo(expectedResponseEntity.getBody());
        assertTrue(response.getBody().success());
    }

    @Test
    public void DeleteUserById_ShouldReturnFalseInApiResponse() {
        ApiResponse apiResponse = new ApiResponse(false,"A problem occurred while deleting user");
        ResponseEntity<ApiResponse> expectedResponseEntity = ResponseEntity
                .badRequest()
                .body(apiResponse);

        doReturn(CompletableFuture.completedFuture(expectedResponseEntity))
                .when(useCaseExecutor)
                .execute(eq(deleteUserUseCase), any(DeleteUserUseCase.InputValues.class), any());

        CompletableFuture<ResponseEntity<ApiResponse>> result = userController.deleteUserById(1L);
        ResponseEntity<ApiResponse> response = result.join();

        assertThat(response.getBody()).isEqualTo(apiResponse);
        assertThat(response.getStatusCode()).isEqualTo(expectedResponseEntity.getStatusCode());
        assertThat(response.getBody()).isEqualTo(expectedResponseEntity.getBody());
        assertFalse(response.getBody().success());
    }

    @Test
    public void updateUser_ShouldReturnExpectedUserResponse() {
        Long id = 1L;
        UserSettingRequest userSettingRequest = new UserSettingRequest("email@email.com",
                "123456&23'éTh", "theo");

        User user = new User();
        UserResponse expectedResponse = UserResponse.from(user);

        doReturn(CompletableFuture.completedFuture(expectedResponse))
                .when(useCaseExecutor)
                .execute(eq(updateUserUseCase), any(UpdateUserUseCase.InputValues.class), any());

        CompletableFuture<UserResponse> result = userController.updateUser(id, userSettingRequest);
        UserResponse response = result.join();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void getUserAuth_ShouldReturnAuthId() {
        Long expectedAuthId = 2L;

        doReturn(2L).when(jwtTokenProvider).getAuthenticateUser();

        CompletableFuture<ResponseEntity<?>> result = userController.getUserAuth();
        ResponseEntity<?> response = result.join();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedAuthId);
    }
}
