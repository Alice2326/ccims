package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.Course;
import com.zhangxiang.ccims.entity.CourseAssign;
import com.zhangxiang.ccims.entity.Student;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.CourseAssignMapper;
import com.zhangxiang.ccims.mapper.CourseMapper;
import com.zhangxiang.ccims.mapper.StudentMapper;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseAssignMapper courseAssignMapper;
    @Resource
    private CourseMapper courseMapper;

    @PostMapping("")
    public Result add(@RequestBody Student student){
        int i = studentMapper.insert(student);
        return MybatisUtil.getResult(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = studentMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PreAuthorize("hasRole('TEACHER') and principal.username.equals(#id.toString()) or hasRole('ADMIN')")
    @PostMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Student student) {
        student.setS_id(id);
        int i = studentMapper.updateById(student);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        Student student = studentMapper.selectById(id);
        return Result.builder().msg(student).code(Code.SUCCESS.getCode()).build();
    }

    // type: a,ca: class -> ca, c
    @GetMapping("")
    public Result getAll(@RequestParam(required = false,defaultValue = "a") String type,
                         @RequestParam(required = false) Integer id) {

        System.out.println(type);
        System.out.println(id);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();

        if (type.equals("a")){
            List<Student> courseAssigns = studentMapper.selectList(queryWrapper);
            return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();

        }else if(type.equals("c")){
            if (id == null){
                return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
            }

            List<Student> students = studentMapper.selectList( queryWrapper.eq("class_id",id));
            return Result.builder().msg(students).code(Code.SUCCESS.getCode()).build();

        }else if(type.equals("ca")){
            if (id == null){
                return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
            }

            List<CourseAssign> courseAssigns = courseAssignMapper.selectList( new QueryWrapper<CourseAssign>().eq("class_id",id));
            if (courseAssigns.size() == 0 ){
                return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();
            }

            List<Integer> integers = new ArrayList<>();

            for (int i = 0; i < courseAssigns.size(); i++) {
                CourseAssign courseAssign = courseAssigns.get(i);
                integers.add(courseAssign.getCourse_id());
            }

            List<Course> courses =  courseMapper.selectList(new QueryWrapper<Course>().in("course_id",integers));

            return Result.builder().msg(courses).code(Code.SUCCESS.getCode()).build();

        }else{
            List<Student> students = studentMapper.selectList(queryWrapper);
            return Result.builder().msg(students).code(Code.SUCCESS.getCode()).build();
        }


    }

}
