package com.teamjo.techeermarket.domain.users.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Social {
    LOCAl("LOCAL", "일반 로그인"),
    GOOGLE("GOOGLE", "구글 로그인");

    private final String key;
    private final String title;
}
