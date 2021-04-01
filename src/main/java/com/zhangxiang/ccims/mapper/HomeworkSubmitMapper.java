package com.zhangxiang.ccims.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangxiang.ccims.entity.Homework;
import com.zhangxiang.ccims.entity.HomeworkSubmit;

import java.util.List;

public interface HomeworkSubmitMapper extends BaseMapper<HomeworkSubmit> {

    public List<HomeworkSubmit> findOneByH_id(Integer h_id);

    public List<HomeworkSubmit> selectAllByS_idAndH_idA(Integer s_id, Integer h_id);


}
