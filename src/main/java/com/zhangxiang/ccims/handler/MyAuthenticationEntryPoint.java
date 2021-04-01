package com.zhangxiang.ccims.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        Result result = Result.builder().code(Code.FAIL.getCode()).msg("你尚未登陆！").build();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().println(new ObjectMapper().writeValueAsString(result));

    }
}
