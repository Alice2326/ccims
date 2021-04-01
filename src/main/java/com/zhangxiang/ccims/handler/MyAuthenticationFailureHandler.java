package com.zhangxiang.ccims.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("exception", exception.getMessage());
        System.out.println(exception.getMessage());
        Result result = Result.builder().code(Code.FAIL.getCode()).msg(exception.getMessage()).build();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(new ObjectMapper().writeValueAsString(result));
    }
}
