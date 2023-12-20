package com.teamjo.techeermarket.global.exception.product;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;


public class FailedToDeleteProductException  extends CustomException {

    public FailedToDeleteProductException() {super(ErrorCode.FAILED_DELETE_PRODUCT); }

}
