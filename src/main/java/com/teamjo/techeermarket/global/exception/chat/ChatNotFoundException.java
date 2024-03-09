package com.teamjo.techeermarket.global.exception.chat;

import com.teamjo.techeermarket.global.exception.CustomException;
import com.teamjo.techeermarket.global.exception.ErrorCode;

public class ChatNotFoundException extends CustomException {
  public ChatNotFoundException() {super(ErrorCode.CHAT_NOT_FOUND); }
}
