package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class CategoryNotFoundException extends CustomException {

    public CategoryNotFoundException() {super(ErrorCode.CATEGORY_NOT_FOUND); }

}
