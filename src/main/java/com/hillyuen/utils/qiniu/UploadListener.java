package com.hillyuen.utils.qiniu;

/*****
 *
 * @author yuanxunxi
 * @Description
 * @version V1.0
 * @Date 2018/12/3 18:30
 ***/
public interface UploadListener {

    void onEvent(FileUploadEvent event);

}
