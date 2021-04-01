package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.CourseAssign;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.CourseAssignMapper;
import com.zhangxiang.ccims.util.FileUtil;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/course_assigns")
public class CourseAssignController {
    @Resource
    private CourseAssignMapper CourseAssignMapper;

    @Resource
    private FileUtil fileUtil;


    @PostMapping("")
    public Result add(@RequestBody CourseAssign CourseAssign){
        int i = CourseAssignMapper.insert(CourseAssign);
        return MybatisUtil.getResult(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = CourseAssignMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PostMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody CourseAssign CourseAssign) {
        CourseAssign.setCa_id(id);
        int i = CourseAssignMapper.updateById(CourseAssign);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        CourseAssign CourseAssign = CourseAssignMapper.selectById(id);
        return Result.builder().msg(CourseAssign).code(Code.SUCCESS.getCode()).build();
    }
    // type: all,s,t
    @GetMapping("")
    public Result getAll(@RequestParam(required = false,defaultValue = "a") String type,
                         @RequestParam(required = false) Integer id, @RequestParam(required = false) String week) {

        System.out.println(type);
        System.out.println(id);
        QueryWrapper<CourseAssign> queryWrapper = new QueryWrapper<>();

        if (type.equals("a")){
            List<CourseAssign> courseAssigns = CourseAssignMapper.selectAllByClass_id(null,null);
            return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();

        }else if(type.equals("c")){
            if (id == null){
                return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
            }

            List<CourseAssign> courseAssigns = CourseAssignMapper.selectAllByClass_id(id,week);
            return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();

        }else if(type.equals("t")){
            if (id == null){
                return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
            }
            List<CourseAssign> courseAssigns = CourseAssignMapper.selectAllByT_id(id, week);
            return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();

        }else{
            List<CourseAssign> courseAssigns = CourseAssignMapper.selectList(queryWrapper);
            return Result.builder().msg(courseAssigns).code(Code.SUCCESS.getCode()).build();
        }


    }

    @PostMapping("/{ca_id}/upload")
    public Result uploadMaterial(@PathVariable Integer ca_id, @RequestParam(name = "files") MultipartFile[] multipartFiles) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();


        StringJoiner stringJoiner = new StringJoiner(";");
        for (int i = 0; i < multipartFiles.length; i++) {
            Path path = Paths.get(fileUtil.getMaterial_path(), String.valueOf(ca_id),
                    localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),multipartFiles[i].getOriginalFilename());
            if(!Files.exists(path.getParent())){
                Path path1 = Files.createDirectories(path.getParent());
                System.out.println(path1.getFileSystem());
                System.out.println(path1);
            }

            multipartFiles[i].transferTo(path);
            stringJoiner.add(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+path.toFile().getAbsolutePath());
        }

        CourseAssign courseAssign = CourseAssignMapper.selectById(ca_id);
        String old_paths = "";
        System.out.println(courseAssign.getFile_paths());
        if (courseAssign.getFile_paths()!=null && !courseAssign.getFile_paths().isBlank()){
            old_paths = courseAssign.getFile_paths()+";";
            System.out.println(old_paths);
        }


        int i = CourseAssignMapper.updateById(CourseAssign.builder().ca_id(ca_id).file_paths(old_paths  + stringJoiner.toString()).build());


        return Result.builder().msg("上传成功").code(Code.SUCCESS.getCode()).build();
    }

    @GetMapping("/{ca_id}/file_list")
    public Result getFileList(@PathVariable Integer ca_id){
        // 根据h_id获取附件列表
        Path path = Paths.get(fileUtil.getMaterial_path(), String.valueOf(ca_id));
        File file = path.toFile();
        List<Map> files= new ArrayList<>();
        CourseAssign courseAssign = CourseAssignMapper.selectById(ca_id);
        if (courseAssign.getFile_paths() == null || courseAssign.getFile_paths().isBlank()) {
            return Result.builder().code(Code.SUCCESS.getCode()).msg(new Integer[]{}).build();
        }
        String[] split = courseAssign.getFile_paths().split(";");
        sort(split);
        for (String s : split) {
            System.out.println(s);
        }
        List<File> list = new ArrayList<>();
        listAllFile(path.toFile(),list);
        int index = 0;
        for (int i = 0; i < split.length; i++) {
            for (File file1 : list) {
                if (split[i].substring(19).equals(file1.getAbsolutePath())) {
                    System.out.println("匹配到："+split[i]);
                    Map map = new HashMap();
                    map.put("index",index);
                    map.put("file_name",file1.getName());
                    map.put("date", split[i].substring(0,19));
                    files.add(map);
                    index++;
                }
            }

        }

//        List<String> paths= new ArrayList<>();
//        if (path.iterator().hasNext()){
//            Path path1 = path.iterator().next();
//            //System.out.println(path1.toAbsolutePath());
//            paths.add(path1.toString());
//            Files.
//        }

        return Result.builder().code(Code.SUCCESS.getCode()).msg(files).build();


    }

    public void listAllFile(File file,List<File> list){

        for (File listFile : file.listFiles()) {
            if (listFile.isFile()){
                list.add(listFile);

            }else if(listFile.isDirectory()){
                listAllFile(listFile,list);

            }

        }

    }

    @GetMapping("/{ca_id}/download")
    public Result download(@PathVariable Integer ca_id, @RequestParam String file_name, HttpServletResponse response) throws IOException {
        CourseAssign courseAssign = CourseAssignMapper.selectById(ca_id);
        if (courseAssign.getFile_paths() == null) {
            return Result.builder().code(Code.FAIL.getCode()).msg("数据库中没有记录！").build();
        }
        String[] split = courseAssign.getFile_paths().split(";");
        System.out.println(split);

        boolean flag = false;
        for (String s : split) {
            File file = new File(s.substring(19));
            if (file.exists()){
                if (file.getName().equals(file_name)){
                    flag = true;
                    // 读到流中
                    InputStream inputStream = new FileInputStream(file);// 文件的存放路径
                    response.reset();
                    response.setContentType("application/octet-stream");
                    String filename = file.getName();
                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
                    ServletOutputStream outputStream = response.getOutputStream();
                    byte[] b = new byte[1024];
                    int len;
                    //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
                    while ((len = inputStream.read(b)) > 0) {
                        outputStream.write(b, 0, len);
                    }
                    inputStream.close();

                }
            }
        }
        if (flag){
            return Result.builder().code(Code.SUCCESS.getCode()).msg("正在下载中").build();
        }
        return Result.builder().code(Code.FAIL.getCode()).msg("未找到文件！").build();

    }

    public void sort(String[] strings) {
        String[] n = new String[strings.length];
        Arrays.sort(strings, (a,b) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (format.parse(a.substring(0,19)).after(format.parse(b.substring(0,19))))
                    return -1;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        });

    }
}
