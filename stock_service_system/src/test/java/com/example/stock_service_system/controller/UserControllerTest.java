package com.example.stock_service_system.controller;

import com.example.stock_service_system.dto.User;
import com.example.stock_service_system.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void CREATE_USER() throws Exception{
        User.UserRequest userRequest = User.UserRequest.builder()
                .name("jaeil")
                .id("jaeilsssss")
                .password("1234567@")
                .nickName("jjjjjj")
                .build();

        User.UserResponse userResponse = User.UserResponse.builder()
                .id("jaeilssss")
                .name("jaeil")
                .nickName("jjjjjj")
                .build();
/*        given(userService.signUp(userRequest))
                .willReturn(userResponse);*/

        mockMvc.perform(post("/user/signUp")
                        .contentType(contentType)
                        .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void GET_USER() throws Exception{
        User.UserRequest userRequest = User.UserRequest.builder()
                .name("jaeil")
                .id("jaeilsssss")
                .password("1234567@")
                .nickName("jjjjjj")
                .build();
        User.UserResponse userResponse = User.UserResponse.builder()
                .id("jaeilssss")
                .name("jaeil")
                .nickName("jjjjjj")
                .build();
        given(userService.signUp(userRequest))
                .willReturn(userResponse);

        mockMvc.perform(get("/user/{id}")
                        .contentType(contentType)
                        .param("id","jaeilssss"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void EDIT_USER() throws Exception{
        User.UserRequest userRequest = User.UserRequest.builder()
                .name("test")
                .id("test")
                .password("1234567@")
                .nickName("test 1")
                .build();
        User.UserResponse userResponse = User.UserResponse.builder()
                .id("test")
                .name("test")
                .nickName("test 1")
                .build();

        User.UserRequest newUserRequest = User.UserRequest.builder()
                .name("test new")
                .id("test")
                .password("1234567@")
                .nickName("test 1")
                .build();
        given(userService.signUp(userRequest))
                .willReturn(userResponse);

        mockMvc.perform(put("/user/test")
                        .contentType(contentType)
                        .content(objectMapper.writeValueAsBytes(newUserRequest)))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    void DELETE_USER() throws Exception{
        User.UserRequest userRequest = User.UserRequest.builder()
                .name("test2")
                .id("testwqwq2")
                .password("1234567@")
                .nickName("test 1")
                .build();
        User.UserResponse userResponse = User.UserResponse.builder()
                .id("testwqwq2")
                .name("test2")
                .nickName("test 1")
                .build();
        given(userService.signUp(userRequest))
                .willReturn(userResponse);

        mockMvc.perform(delete("/user/test")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print());
    }


}