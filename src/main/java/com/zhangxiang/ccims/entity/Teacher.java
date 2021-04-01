package com.zhangxiang.ccims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.zhangxiang.ccims.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@TableName(value = "teacher")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @TableId(type = IdType.AUTO)
    @IsKey
    @IsAutoIncrement
    private Integer t_id;

    @TableField
    private String name;

    @TableField
    @Column(type = MySqlTypeConstant.VARCHAR)
    private Gender gender;


    @TableField
    private String password;

    @TableField(exist = false)
    private List<CourseAssign> courseAssignList;

    public String getPassword() {
        return password;
    }


}
