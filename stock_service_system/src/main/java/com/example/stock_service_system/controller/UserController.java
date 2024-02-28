package com.example.stock_service_system.controller;


import com.example.stock_service_system.dto.User;
import com.example.stock_service_system.service.UserService;
import com.mysql.cj.log.Log;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableJpaAuditing
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signUp")
    public User.UserResponse signUp(
            @Valid @RequestBody User.UserRequest userRequest){
        log.info("signUP");
        return userService.signUp(userRequest);
    }

    @GetMapping("/user/{id}")
    public User.UserResponse getUserInfo(@PathVariable String id){
        log.info("getUserInfo");
        return userService.getUserInfo(id);
    }
    @PutMapping("/user/{id}")
    public User.UserResponse editUserInfo(
            @PathVariable String id,
            @Valid @RequestBody User.UserRequest request){
        log.info("editUserInfo  // @"+id);
        return userService.editUserInfo(id , request);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserInfo(
            @PathVariable final String id
    ){
        log.info("deleteUserInfo");
        userService.deleteUserInfo(id);
    }


}
