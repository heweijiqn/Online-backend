package com.hwj.classroom.until;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {


    @Value("${tencent.cloud.secretId}")
    private String secretId;

    @Value("${tencent.cloud.secretKey}")
    private String secretKey;




    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = secretId;
        ACCESS_KEY_SECRET = secretKey;
    }
}
