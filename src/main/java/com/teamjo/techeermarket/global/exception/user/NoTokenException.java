package com.teamjo.techeermarket.global.exception.user;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class NoTokenException  extends CustomException {
    public NoTokenException() {super(ErrorCode.THERE_IS_NO_TOKEN);}
}
