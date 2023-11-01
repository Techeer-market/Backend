package com.teamjo.techeermarket.global.exception.global;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class ImageNotFoundException extends CustomException {

    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }

}
