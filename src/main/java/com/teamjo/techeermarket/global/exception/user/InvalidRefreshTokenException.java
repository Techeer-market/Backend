package com.teamjo.techeermarket.global.exception.user;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class InvalidRefreshTokenException  extends CustomException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_TOKEN_EXCEPTION);
    }
}
