package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class NotYourProductException extends CustomException {
    public NotYourProductException() {super(ErrorCode.PRODUCT_IS_NOT_YOURS); }
}
