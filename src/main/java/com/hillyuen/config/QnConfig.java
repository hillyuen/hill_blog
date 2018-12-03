package com.hillyuen.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*****
 * 七牛配置读取
 * @author yuanxunxi
 * @Description
 * @version V1.0
 * @Date 2018/12/3 18:45
 ***/
@Component
@PropertySource(value = "classpath:config/qiniu.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@Data
public class QnConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucketName;
    @Value("${qiniu.upHost}")
    private String upHost;
    @Value("${qiniu.upHostBackup}")
    private String upHostBackup;
    @Value("${qiniu.area}")
    private String area;

}
