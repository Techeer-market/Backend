package com.teamjo.techeermarket.global.exception.user;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class UserEmailAlreadyExistsException extends CustomException {

    public UserEmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
