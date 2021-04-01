package com.zhangxiang.ccims.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;
import jdk.jfr.Category;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutHandler implements LogoutHandler {

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        Result result = Result.builder().code(Code.SUCCESS.getCode()).msg("ok").build();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().println(new ObjectMapper().writeValueAsString(result));



    }
}
