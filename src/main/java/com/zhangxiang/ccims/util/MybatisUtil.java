package com.zhangxiang.ccims.util;

import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;

public class MybatisUtil {

    public static Result getResult(int i){
        Result<String> result = new Result<>();
        if (i == 1){
            result.setCode(Code.SUCCESS.getCode());
            result.setMsg("操作成功");
        }else{
            result.setCode(Code.FAIL.getCode());
            result.setMsg(String.valueOf(i));
        }

        return result;



    }
}
