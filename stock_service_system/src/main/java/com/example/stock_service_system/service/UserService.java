package com.example.stock_service_system.service;

import com.example.stock_service_system.code.UserErrorCode;
import com.example.stock_service_system.dto.User;
import com.example.stock_service_system.entity.UserInfo;
import com.example.stock_service_system.exeception.UserException;
import com.example.stock_service_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User.UserResponse signUp(User.UserRequest request){
        validateSignUpUser(request);
        return User.UserResponse
                .fromEntity(userRepository
                        .save(User.toUserEntityFromUserRequest(request)));
    }
    @Transactional
    public User.UserResponse getUserInfo(String id){
        return User.UserResponse.fromEntity(getUserInfoData(id));
    }
    @Transactional
    public UserInfo getUserInfoData(final String id){
        return userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.NO_USER)
        );
    }

    @Transactional
    public void validateSignUpUser(User.UserRequest request){
        userRepository.findById(request.getId()).ifPresent(
                (userInfo -> {
                    throw new UserException(UserErrorCode.DUPLICATED_USER_ID);
                })
        );
    }


    @Transactional
    public User.UserResponse editUserInfo(
            final String id,
            User.UserRequest userRequest
    ){
        return User.UserResponse.fromEntity(setUserInfoData(
                userRequest,
                getUserInfoData(id)
        ));
    }

    public UserInfo setUserInfoData(
            User.UserRequest userRequest,
            UserInfo userInfo
    ){
      userInfo.setName(userRequest.getName());
      userInfo.setPassword(userRequest.getPassword());
      userInfo.setNickName(userRequest.getNickName());
      return userInfo;
    }

    @Transactional
    public void deleteUserInfo(final String id){
        userRepository.delete(getUserInfoData(id));
    }


}
