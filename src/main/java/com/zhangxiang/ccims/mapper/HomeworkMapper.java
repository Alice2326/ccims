package com.zhangxiang.ccims.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangxiang.ccims.entity.Homework;

import java.util.List;

public interface HomeworkMapper extends BaseMapper<Homework> {


    public List<Homework> listHomeworkByCa_id(Integer ca_id);

}
