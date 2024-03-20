package com.mddcore.usecases.it;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

public abstract class BaseControllerTest {

    protected abstract MockMvc getMockMvc();

    private RequestBuilder methodRequestBuilderWithCookies(String jwt, String refreshJwt, MockHttpServletRequestBuilder method) throws Exception {
        return asyncDispatch(getMockMvc().perform(method.header(HttpHeaders.COOKIE, jwt)
                .header(HttpHeaders.COOKIE, refreshJwt)).andExpect(request()
                .asyncStarted())
                .andReturn());
    }

    protected RequestBuilder asyncGetRequestWithCookies(String url, String jwtToken, String refreshToken) throws Exception {
        return methodRequestBuilderWithCookies(jwtToken, refreshToken, get(url));
    }

    protected RequestBuilder asyncDeleteRequest(String url, String token, String refreshToken) throws Exception {
        return methodRequestBuilderWithCookies(token, refreshToken, delete(url));
    }


    protected RequestBuilder asyncGetRequestWithoutCookie(String url) throws Exception {
        return asyncDispatch(getMockMvc().perform(get(url))
                .andExpect(request().asyncStarted())
                .andReturn());
    }

    protected RequestBuilder asyncPostRequestWithCookies(String url, String payload, String jwtToken, String refreshToken) throws Exception {
        MockHttpServletRequestBuilder content = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .header(HttpHeaders.COOKIE, jwtToken)
                .header(HttpHeaders.COOKIE, refreshToken);

        return asyncDispatch(getMockMvc()
                .perform(content)
                .andExpect(request()
                .asyncStarted())
                .andReturn());
    }

    protected RequestBuilder asyncPostRequestWithoutCookie(String url, String payload) throws Exception {
        return asyncDispatch(
                getMockMvc().perform(
                                post(url).contentType(MediaType.APPLICATION_JSON).content(payload))
                        .andExpect(request().asyncStarted())
                        .andReturn());
    }
}
