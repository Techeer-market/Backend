package com.teamjo.techeermarket.global.exception.user;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class InvalidTokenException  extends CustomException {
    public InvalidTokenException() {
        super(ErrorCode.INVALIED_TOKEN_EXCEPTION);
    }
}
