package com.mddpresenter.usecases.it;

import com.mddinfrastructure.MddApiApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MddApiApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return this.mockMvc;
    }

    @Value("${mdd.app.jwtCookieName}")
    private String cookieName;

    @Value("${mdd.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

//    @Test
//    @Order(1)
//    @WithMockUser
//    public void getUserById_ShouldRetrieveUser_WithStatusValidId() throws Exception {
//        mockMvc.perform(get("/api/user/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(1)
//    @WithMockUser
//    public void deleteUserById_ShouldDeleteUser_WithStatusValidId() throws Exception {
//        RequestBuilder requestBuilder =  asyncDeleteRequest("/api/user/1", cookieName, jwtRefreshCookie);
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("Delete user successfully"));
//    }
}
