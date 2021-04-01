package com.zhangxiang.ccims.enums;

public enum Rules {
    ADMIN("管理员"),TEACHER("教师"),STUDENT("学生");

    private final String name;

    public String getName() {
        return name;
    }

    Rules(String name){
        this.name = name;
    }
}
