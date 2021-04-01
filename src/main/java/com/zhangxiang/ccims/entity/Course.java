package com.zhangxiang.ccims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@TableName(value = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course
{
    @TableId(type = IdType.AUTO)
    @IsKey
    @IsAutoIncrement
    private Integer course_id;

    @TableField
    private String course_name;

    @TableField
    private boolean is_assign;






}
