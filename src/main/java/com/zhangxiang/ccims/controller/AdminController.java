package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.AClass;
import com.zhangxiang.ccims.entity.Admin;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.AdminMapper;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @PostMapping("")
    public Result add(@RequestBody Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int insert = adminMapper.insert(admin);
        return MybatisUtil.getResult(insert);
    }


    @GetMapping("")
    public Result getAll() {

        List<Admin> admins = adminMapper.selectList(Wrappers.emptyWrapper());
        return Result.builder().msg(admins).code(Code.SUCCESS.getCode()).build();
    }
}
