package com.zhangxiang.ccims;

import com.zhangxiang.ccims.entity.*;
import com.zhangxiang.ccims.enums.CourseTime;
import com.zhangxiang.ccims.enums.Gender;
import com.zhangxiang.ccims.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootTest
class CcimsApplicationTests {


    @Resource
    private AdminMapper adminMapper;
    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private HomeworkMapper homeworkMapper;

    @Resource
    private AdminByXmlMapper adminByXmlMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private AClassMapper aClassMapper;

    @Test
    void contextLoads() {

        //System.out.println(adminByXmlMapper.insert(Admin.builder().name("zzz111").password("23261").build()));
        System.out.println(adminByXmlMapper.listAdmin());
    }


    @Test
    public void TeacherTest(){
        System.out.println(teacherMapper.insert(Teacher.builder().name("zzz").gender(Gender.MAN).build()));

    }

    @Test
    public void StudentTest(){
        //System.out.println(studentMapper.insert(Student.builder().name("zzz").gender(Gender.MAN).class_id(1).build()));
        Student student = studentMapper.selectById(2);

        System.out.println(student);

    }

    @Test
    public void AClassTest(){
        System.out.println(aClassMapper.insert(AClass.builder().name("网工2班").build()));

    }


    @Test
    public void HomeworkTest(){
        System.out.println(homeworkMapper.insert(Homework.builder().name("测试作业1").create_date(new Date()).build()));

    }

    @Resource
    private CourseAssignMapper courseAssignMapper;

    @Test
    public void CourseAssignTest(){
        System.out.println(courseAssignMapper.insert(CourseAssign.builder().course_id(1).ca_time(CourseTime.ONE).class_id(1).t_id(1).build()));

    }

    @Test
    public void MyTest(){
        String a = "2021-03-30 14:00:001111";
        System.out.println(a.substring(0,19));

    }








}
