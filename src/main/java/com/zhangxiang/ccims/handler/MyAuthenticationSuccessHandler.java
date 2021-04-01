package com.zhangxiang.ccims.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.enums.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        this.logger.info(String.format("IP %s，用户 %s， 于 %s 成功登录系统。", httpServletRequest.getRemoteHost(), authentication.getName(), LocalDateTime.now()));
        HttpSession session = httpServletRequest.getSession();
        //session.setAttribute("user","1");
        Result result = Result.builder().code(Code.SUCCESS.getCode()).msg(authentication.getName()).build();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().println(new ObjectMapper().writeValueAsString(result));



    }
}
