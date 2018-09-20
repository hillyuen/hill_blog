package com.hillyuen.model.enums;

/**
 * @author : Hill_Yuen
 * @date : 2018/7/14
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(1),

    /**
     * 失败
     */
    FAIL(0);

    Integer code;

    ResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
