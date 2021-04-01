package com.zhangxiang.ccims.enums;

public enum Code {
    SUCCESS(200),FAIL(500);

    public int getCode() {
        return code;
    }

    private final int code;
    Code(int code){
        this.code = code;


    }
}
