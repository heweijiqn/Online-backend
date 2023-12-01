/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/2 17:19
 */


package com.hwj.classroom.vod.config;


import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class UploadConfig {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth(){
        return Auth.create(accessKey,secretKey);
    }

    @Bean
    public UploadManager getUploadManager(){
        return new UploadManager(new Configuration());
    }
}
