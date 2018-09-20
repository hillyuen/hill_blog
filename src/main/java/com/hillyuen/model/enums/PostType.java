package com.hillyuen.model.enums;

/**
 * @author : Hill_Yuen
 * @date : 2018/7/1
 */
public enum PostType {

    /**
     * 文章
     */
    POST_TYPE_POST("post"),

    /**
     * 页面
     */
    POST_TYPE_PAGE("page");

    private String desc;

    PostType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
