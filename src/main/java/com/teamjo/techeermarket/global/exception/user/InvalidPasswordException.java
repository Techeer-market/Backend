package com.teamjo.techeermarket.global.exception.user;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class InvalidPasswordException  extends CustomException {
    public InvalidPasswordException() {
        super(ErrorCode.PASSWORD_IS_INVALID);
    }
}

