package com.mddcore.usecases.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mddcore.usecases.auth.IPasswordEncodeFinal;
import com.mddcore.usecases.auth.SignInRequest;
import com.mddinfrastructure.MddApiApplication;
import com.mddinfrastructure.request.UserSettingRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MddApiApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerIntegrationTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private IPasswordEncodeFinal passwordEncodeFinal;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }
    @Test
    @Order(1)
    public void RegisterAndLoginUser_ShouldRegisterUserThenLogin_WithStatusOkForBoth() throws Exception {
        UserSettingRequest settingRequest = new UserSettingRequest(
                "theokennel6@gmail.com",
                "123456&Theo",
                "theo");

        String signInRequestJson = new ObjectMapper().writeValueAsString(settingRequest);
        RequestBuilder requestBuilder = asyncPostRequestWithoutCookie("/api/auth/register", signInRequestJson);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(settingRequest.email())));


        SignInRequest signInReq = new SignInRequest("theokennel6@gmail.com", "123456&Theo");
        String loginRequestJson = new ObjectMapper().writeValueAsString(signInReq);
        RequestBuilder request = asyncPostRequestWithoutCookie("/api/auth/login", loginRequestJson);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath(("$.id"), is(1)));
    }
    @Test
    @Order(2)
    public void RegisterUser_ShouldThrowUserAlreadyExist( ) throws Exception {
        UserSettingRequest settingRequest = new UserSettingRequest(
                "theokennel6@gmail.com",
                "123456&Theo",
                "theo");
        String signInRequestJson = new ObjectMapper().writeValueAsString(settingRequest);
        RequestBuilder requestBuilder = asyncPostRequestWithoutCookie("/api/auth/register", signInRequestJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User email already exist"))
                .andExpect(jsonPath("$.success").value(false))
                .andReturn();

    }
}
