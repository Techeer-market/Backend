package com.teamjo.techeermarket.global.S3;

import lombok.Getter;

@Getter
public enum BucketDir {
    PRODUCT("product"),         // 상품 사진
    PROFILE("profile"), ;   //프로필 이미지

    private String dirName;

    BucketDir(String dirName) {
        this.dirName = dirName;
    }
}
