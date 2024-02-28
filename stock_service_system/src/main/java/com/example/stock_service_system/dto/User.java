package com.example.stock_service_system.dto;

import com.example.stock_service_system.entity.UserInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


public class User {
    public static UserInfo toUserEntityFromUserRequest(UserRequest request){
        return UserInfo.builder()
                .name(request.name)
                .id(request.id)
                .password(request.password)
                .nickName(request.nickName)
                .build();
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRequest{
        @NotNull
        String name;

        @NotNull
        String id;

        @NotNull
        String nickName;

        @NotNull
        @Size(min = 8 , max = 13 , message = "password size must 3~50")
        String password;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponse{
        String name;
        String id;
        String nickName;

        public static UserResponse fromEntity(UserInfo user){
            return UserResponse.builder()
                    .name(user.getName())
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .build();
        }
    }


}
