package com.zhangxiang.ccims.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.AClass;
import com.zhangxiang.ccims.entity.Student;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.AClassMapper;
import com.zhangxiang.ccims.mapper.StudentMapper;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class AClassController {
    @Resource
    private AClassMapper aClassMapper;

    @PostMapping("")
    public Result add(@RequestBody AClass aClass){
        int i = aClassMapper.insert(aClass);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        AClass aClass = aClassMapper.selectById(id);
        return Result.builder().msg(aClass).code(Code.SUCCESS.getCode()).build();
    }

    @GetMapping("")
    public Result getAll() {
        List<AClass> aClass = aClassMapper.selectList(Wrappers.emptyWrapper());
        return Result.builder().msg(aClass).code(Code.SUCCESS.getCode()).build();
    }

}
