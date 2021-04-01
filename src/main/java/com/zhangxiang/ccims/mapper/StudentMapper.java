package com.zhangxiang.ccims.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangxiang.ccims.entity.AClass;
import com.zhangxiang.ccims.entity.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

public interface StudentMapper extends BaseMapper<Student> {

    public Student findStudentBySID(Integer s_id);

}
