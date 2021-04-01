package com.zhangxiang.ccims.service;

import com.zhangxiang.ccims.entity.Admin;
import com.zhangxiang.ccims.entity.Student;
import com.zhangxiang.ccims.entity.Teacher;
import com.zhangxiang.ccims.mapper.AdminMapper;
import com.zhangxiang.ccims.mapper.StudentMapper;
import com.zhangxiang.ccims.mapper.TeacherMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SimpleUserDetailsService implements UserDetailsService {

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private StudentMapper studentMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] usernameAndDomain = StringUtils.split(
                username, String.valueOf(Character.LINE_SEPARATOR));
        if (usernameAndDomain == null || usernameAndDomain.length != 2) {
            throw new UsernameNotFoundException("用户名和角色必须全部提供！");
        }

        switch (usernameAndDomain[1]){
            case "ADMIN":
                Admin admin = adminMapper.selectById(usernameAndDomain[0]);
                if (admin == null)
                    throw  new UsernameNotFoundException("用户名或密码错误");
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new User(admin.getName(),admin.getPassword(),authorities);
            case "TEACHER":
                Teacher teacher = teacherMapper.selectById(usernameAndDomain[0]);
                if (teacher == null)
                    throw new UsernameNotFoundException("用户名或密码错误");
                List<SimpleGrantedAuthority> authorities1 = new ArrayList<>();
                authorities1.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                System.out.println(teacher);
                return new User(String.valueOf(teacher.getT_id()),teacher.getPassword(),authorities1);
            case "STUDENT":
                Student student = studentMapper.selectById(usernameAndDomain[0]);
                if (student == null)
                    throw  new UsernameNotFoundException("用户名或密码错误");
                List<SimpleGrantedAuthority> authorities2 = new ArrayList<>();
                authorities2.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
                return new User(String.valueOf(student.getS_id()),student.getPassword(),authorities2);
            default:
                throw  new UsernameNotFoundException("请选择正确的角色！");


        }



    }
}
