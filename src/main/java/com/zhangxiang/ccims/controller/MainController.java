package com.zhangxiang.ccims.controller;


import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Rules;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController
{


    public Result login(Integer id,String password,String role){
        Rules rules = Rules.valueOf(role);
        switch (rules){
            case ADMIN:
            case TEACHER:
            case STUDENT:
                break;
        }


        return null;
    }
}
