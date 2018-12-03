package com.hillyuen.utils.qiniu;

import lombok.Data;

import java.io.File;

/*****
 * 文件上传相关事件
 * @author yuanxunxi
 * @Description
 * @version V1.0
 * @Date 2018/12/3 18:33
 ***/
@Data
public class FileUploadEvent {

    /**
     * 事件类型 @FileUploadEventType
     */
    private Integer eventType;

    /**
     * 文件上传的相对路径 相对域名
     */
    private String filePath;

    /**
     * 上传的文件实体
     */
    private File file;

}
