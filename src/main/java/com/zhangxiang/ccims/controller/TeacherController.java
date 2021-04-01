package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.Student;
import com.zhangxiang.ccims.entity.Teacher;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.StudentMapper;
import com.zhangxiang.ccims.mapper.TeacherMapper;
import com.zhangxiang.ccims.util.MybatisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("教师管理")
@RestController
public class TeacherController{

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @ApiOperation("添加教师")
    @PostMapping("/teachers")
    public Result add(@RequestBody Teacher teacher){
        System.out.println(teacher);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        int i = teacherMapper.insert(teacher);
        return MybatisUtil.getResult(i);
    }
    @ApiOperation("根据id删除教师")
    @DeleteMapping("/teachers/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = teacherMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PreAuthorize("hasRole('TEACHER') and principal.username.equals(#id.toString()) or hasRole('ADMIN')")
    @ApiOperation("更新教师信息")
    @PutMapping("/teachers/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Teacher teacher) {
        System.out.println(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        teacher.setT_id(id);
        int i = teacherMapper.updateById(teacher);
        return MybatisUtil.getResult(i);
    }

    @ApiOperation("根据id获取教师信息")
    @GetMapping("/teachers/{id}")
    public Result get(@PathVariable Integer id) {
        Teacher teacher = teacherMapper.selectById(id);
        return Result.builder().msg(teacher).code(Code.SUCCESS.getCode()).build();
    }

    @ApiOperation("获取全部教师信息")
    @GetMapping("/teachers")
    public Result getAll() {
        List<Teacher> teachers = teacherMapper.selectList(Wrappers.emptyWrapper());
        return Result.builder().msg(teachers).code(Code.SUCCESS.getCode()).build();
    }
}
