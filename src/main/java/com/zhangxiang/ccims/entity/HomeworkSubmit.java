package com.zhangxiang.ccims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@TableName(value = "homework_submit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkSubmit {


    @TableId(type = IdType.AUTO)
    @IsKey
    @IsAutoIncrement
    private Integer hs_id;

    @TableField
    private Integer h_id;

    @TableField
    @Column(type= MySqlTypeConstant.DATETIME)
    private Date submit_time;
    @TableField
    private String comment;
    @TableField
    private Integer s_id;
    @TableField
    private boolean is_check;
    @TableField
    private String feedback;
    @TableField
    private String file_paths;

    @TableField(exist = false)
    private Student student;



}
