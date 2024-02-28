package com.example.stock_service_system.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    NO_USER("해당되는 유저가 없습니다"),
    DUPLICATED_USER_ID("중복되는 ID가 있습니다"),
    INVALID_REQUEST("잘못된 요청입니다");

    private final String message;
}
