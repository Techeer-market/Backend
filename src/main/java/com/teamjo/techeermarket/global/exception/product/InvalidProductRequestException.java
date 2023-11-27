package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class InvalidProductRequestException  extends CustomException {

    public InvalidProductRequestException() {super(ErrorCode.INVALID_PRODUCT_REQUEST); }

}
