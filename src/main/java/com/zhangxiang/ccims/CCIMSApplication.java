package com.zhangxiang.ccims;

import com.zhangxiang.ccims.util.FileUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.annotation.Resource;
import java.io.File;

@SpringBootApplication
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*","com.zhangxiang.ccims.mapper"})
@ComponentScan(basePackages = {"com.gitee.sunchenbin.mybatis.actable.manager.*","com.zhangxiang.ccims"})
@EnableOpenApi
public class CCIMSApplication {

    public static void main(String[] args) {

        SpringApplication.run(CCIMSApplication.class, args);
    }

}
