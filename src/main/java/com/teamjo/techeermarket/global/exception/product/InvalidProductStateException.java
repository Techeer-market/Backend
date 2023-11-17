package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class InvalidProductStateException extends CustomException {

    public InvalidProductStateException() {super(ErrorCode.INVALID_PRODUCT_STATE); }

}
