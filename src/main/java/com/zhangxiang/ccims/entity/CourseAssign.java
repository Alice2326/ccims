package com.zhangxiang.ccims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.zhangxiang.ccims.enums.CourseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@TableName(value = "course_assign")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssign {

    @TableId(type = IdType.AUTO)
    @IsKey
    @IsAutoIncrement
    private Integer ca_id;

    @TableField
    private Integer course_id;

    @TableField
    private Integer t_id;

    @TableField
    private Integer class_id;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR)
    private CourseTime ca_time;

    @TableField
    private String comment;

    @TableField
    @Column(type = MySqlTypeConstant.LONGTEXT,length = 65535)
    private String file_paths;

    @TableField
    private String week;

    @TableField(exist = false)
    private Course course;
    @TableField(exist = false)
    private Teacher teacher;

    @TableField(exist = false)
    private AClass aclass;

    @TableField(exist = false)
    private List<Student> students;


    @TableField(exist = false)
    private List<Homework> homework;


}
