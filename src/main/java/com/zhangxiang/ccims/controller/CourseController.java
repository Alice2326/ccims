package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.Course;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.CourseMapper;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Resource
    private CourseMapper CourseMapper;


    @PostMapping("")
    public Result add(@RequestBody Course course){
        course.set_assign(false);
        int i = CourseMapper.insert(course);
        return MybatisUtil.getResult(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = CourseMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PostMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Course Course) {
        Course.setCourse_id(id);
        int i = CourseMapper.updateById(Course);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        Course Course = CourseMapper.selectById(id);
        return Result.builder().msg(Course).code(Code.SUCCESS.getCode()).build();
    }
    @GetMapping("")
    public Result getAll() {
        List<Course> courses = CourseMapper.selectList(Wrappers.emptyWrapper());
        return Result.builder().msg(courses).code(Code.SUCCESS.getCode()).build();
    }
}
