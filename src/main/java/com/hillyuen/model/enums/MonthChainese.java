package com.hillyuen.model.enums;

/**
 * @author : Hill_Yuen
 * @date : 2018/9/1
 */
public enum MonthChainese {

    YI_YUE("1","一月"),
    ER_YUE("2","二月"),
    SAN_YUE("3","三月"),
    SI_YUE("4","四月"),
    WU_YUE("5","五月"),
    LIU_YUE("6","六月"),
    QI_YUE("7","七月"),
    BA_YUE("8","八月"),
    JIU_YUE("9","九月"),
    SHI_YUE("10","十月"),
    SHIYI_YUE("11","十一月"),
    SHIER_YUE("12","十二月"),

    ;
    /**
     * 阿拉伯数字月份
     */
    private String num;

    /**
     * 中文月份
     */
    private String month;

    MonthChainese(String num, String month) {
        this.num = num;
        this.month = month;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public static String getChainsesMonth(String month) {
        String result = "";
        for (MonthChainese monthChanese : values()) {
            if (month.equals(monthChanese.getNum())) {
                result = monthChanese.getMonth();
            }
        }
        return result;
    }
}
