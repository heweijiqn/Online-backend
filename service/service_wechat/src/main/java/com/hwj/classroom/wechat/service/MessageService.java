package com.hwj.classroom.wechat.service;

import java.util.Map;

public interface MessageService {
    String receiveMessage(Map<String, String> param);

    void pushPayMessage(long l);
}
