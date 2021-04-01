package com.zhangxiang.ccims.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "admin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @TableId(type = IdType.INPUT)
    private String name;

    @TableField
    @IsNotNull
    private String password;
}
