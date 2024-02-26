package com.example.stock_service_system.exeception;

import com.example.stock_service_system.code.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private UserErrorCode userErrorCode;
    private String message;

    public UserException(UserErrorCode userErrorCode){
        super(userErrorCode.getMessage());
        this.userErrorCode = userErrorCode;
        this.message = userErrorCode.getMessage();
    }
}
