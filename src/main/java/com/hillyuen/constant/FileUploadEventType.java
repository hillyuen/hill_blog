package com.hillyuen.constant;

/*****
 * 文件上传事件类型 操作时约定默认是上传事件
 * @author yuanxunxi
 * @Description
 * @version V1.0
 * @Date 2018/12/3 18:38
 ***/
public enum FileUploadEventType {
    /**
     * 文件上传
     */
    FILE_UPLOAD(1, "文件上传"),
    /**
     * 文件删除
     */
    FILE_DELETE(2, "文件删除"),

    ;

    private Integer type;

    private String desc;

    FileUploadEventType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
