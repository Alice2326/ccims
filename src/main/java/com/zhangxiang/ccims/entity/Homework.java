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
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@TableName(value = "homework")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Homework {

    @TableId(type = IdType.AUTO)
    @IsKey
    @IsAutoIncrement
    private Integer h_id;
    @TableField
    private String name;
    @TableField
    private String comment;
    @TableField
    private Integer ca_id;
    @TableField
    @Column(type= MySqlTypeConstant.DATETIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expire_date;
    @TableField
    @Column(type= MySqlTypeConstant.DATETIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
    @TableField
    private Integer download_count;
    @TableField
    @Column(type = MySqlTypeConstant.LONGTEXT,length = 65535)
    private String file_paths;

    @TableField(exist = false)
    private boolean isSubmit;

    @TableField(exist = false)
    private List<HomeworkSubmit> homeworkSubmitList;

}
