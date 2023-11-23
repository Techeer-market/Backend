package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException() {super(ErrorCode.PRODUCT_NOT_FOUND); }
}
