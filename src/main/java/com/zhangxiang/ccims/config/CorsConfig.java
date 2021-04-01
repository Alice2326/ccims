package com.zhangxiang.ccims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        //1.添加Cors配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:9528"); //设置允许跨域访问服务端的客户端地址
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);//设置允许的请求方式
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);//设置访问哪些请求头
        corsConfiguration.setAllowCredentials(true);//允许携带cookie跨域
        //2.为URL添加映射路径
        UrlBasedCorsConfigurationSource ubccs = new UrlBasedCorsConfigurationSource();
        ubccs.registerCorsConfiguration("/**",corsConfiguration);
        //3.返回重新定义好的corsfilter
        return new CorsFilter(ubccs);
    }
}
