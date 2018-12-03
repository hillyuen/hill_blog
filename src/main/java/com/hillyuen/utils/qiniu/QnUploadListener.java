package com.hillyuen.utils.qiniu;

import com.hillyuen.config.QnConfig;
import com.hillyuen.constant.FileUploadEventType;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/*****
 * 七牛文件监听管理
 * @author yuanxunxi
 * @Description
 * @version V1.0
 * @Date 2018/12/3 18:42
 ***/
@Component
@Slf4j
public class QnUploadListener implements UploadListener {

    @Autowired
    private QnConfig qnConfig;

    @Override
    public void onEvent(FileUploadEvent event) {
        if (event == null) {
            return;
        }
        if (event.getEventType() == FileUploadEventType.FILE_DELETE.getType().intValue()) {
            // 文件删除

        } else {
            // 文件上传
            Configuration cfg = new Configuration();
            Auth mac = Auth.create(qnConfig.getAccessKey(), qnConfig.getSecretKey());
            String token = mac.uploadToken(qnConfig.getBucketName());
            cfg.connectTimeout = 60;
            cfg.writeTimeout = 60;
            //Zone zone = new Zone();//upHost, upHostBackup);
            if("z0".equals(qnConfig.getArea())){
                cfg.zone = Zone.zone0();
            }else if("z1".equals(qnConfig.getArea())){
                cfg.zone = Zone.zone1();
            }else if("z2".equals(qnConfig.getArea())){
                cfg.zone = Zone.zone2();
            }else if("na0".equals(qnConfig.getArea())){
                cfg.zone = Zone.zoneNa0();
            }else if("as0".equals(qnConfig.getArea())){
                cfg.zone = Zone.zoneAs0();
            }else{
                cfg.zone = Zone.zone0();
            }
            UploadManager uploadManager = new UploadManager(cfg);
            File file = event.getFile();
            String key = generateKey(event.getFilePath());
            log.info("key" + key);
            try {
                Response response = uploadManager.put(file, key, token);
                log.info("上传图片:{}", response.toString());
                log.info(response.bodyString());
                if ( response.isOK() ) {
                    // 源文件删除掉
                    file.delete();
                } else {
                    // if error
                }
            } catch (QiniuException e) {
                Response res = e.response;
                // 请求失败
                log.error(res.toString());
                try {
                    // 响应信息
                    log.error(res.bodyString());
                } catch (QiniuException e1) {
                    // ignore
                }
            }
        }
    }

    /**
     * 生成key
     * @param filePath
     * @return
     */
    private String generateKey(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new IllegalArgumentException("文件名为空，无法上传到七牛云存储");
        }
        return filePath;
    }
}
