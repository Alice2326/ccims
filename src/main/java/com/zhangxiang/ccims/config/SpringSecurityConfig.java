package com.zhangxiang.ccims.config;

import com.zhangxiang.ccims.filter.SimpleAuthenticationFilter;
import com.zhangxiang.ccims.handler.*;
import com.zhangxiang.ccims.service.SimpleUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Provider;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @author knightzz
 * @date 2021/3/5 10:59
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SimpleUserDetailsService simpleUserDetailsService;
//    @Resource
//    private TeacherLoginService teacherLoginService;
//    @Resource
//    private StudentLoginService studentLoginService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MyAuthenticationSuccessHandler mySuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyLogoutHandler myLogoutHandler;

    @Resource
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(simpleUserDetailsService);
        dao.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(dao);
    }

    //    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodePassword = passwordEncoder.encode("admin");
//        UserDetailsManagerConfigurer<AuthenticationManagerBuilder, org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>>.UserDetailsBuilder w = auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(encodePassword).roles("ADMIN")
//                .and()
//                .withUser("student")
//                .password(encodePassword).roles("STUDENT")
//                .and()
//                .withUser("teacher")
//                .password(encodePassword).roles("TEACHER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(mySuthenticationSuccessHandler);
        http.authorizeRequests()
//                .antMatchers(
//                        "/teachers", "/teachers/",
//                        "/admins", "/admins/",
//                        "/students", "/students/"
//                ).hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.GET,
//                        "/homeworks").hasAnyRole("STUDENT", "TEACHER")
                .antMatchers("/login*", "/admins*",
                        "/teachers", "/teachers/",
                        "/admins", "/admins/",
                        "/students", "/students/","/classes*","/*",
                        "/course_assigns/upload/*",
                        "/homeworks/*/file_list","/homeworks/*/download","/*/*",
                        "/*/*/*").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").addLogoutHandler(myLogoutHandler)
                .and().exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler)
                .and().csrf().disable();


    }

    public SimpleAuthenticationFilter authenticationFilter() throws Exception {
        SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(mySuthenticationSuccessHandler);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminLoginService).passwordEncoder(passwordEncoder);
//        auth.userDetailsService(teacherLoginService).passwordEncoder(passwordEncoder);
//        auth.userDetailsService(studentLoginService).passwordEncoder(passwordEncoder);
//    }

}
