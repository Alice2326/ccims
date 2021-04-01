package com.zhangxiang.ccims.mapper;

import com.zhangxiang.ccims.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.util.List;


public interface AdminByXmlMapper {

    @Insert("INSERT INTO admin(name,password) VALUES (#{name},#{password})")
    public int insert(Admin admin);

    public List<Admin> listAdmin();
}
