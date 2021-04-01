package com.zhangxiang.ccims.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        Result result = Result.builder().code(Code.FAIL.getCode()).msg(e.getMessage()).build();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().println(new ObjectMapper().writeValueAsString(result));

    }
}
