package com.zhangxiang.ccims.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangxiang.ccims.entity.CourseAssign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseAssignMapper extends BaseMapper<CourseAssign> {

    public List<CourseAssign> selectAllByClass_id(Integer class_id,String week);
    public List<CourseAssign> selectAllByT_id(Integer t_id,String week);

}
