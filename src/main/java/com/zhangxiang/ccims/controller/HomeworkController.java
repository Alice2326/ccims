package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.CourseAssign;
import com.zhangxiang.ccims.entity.Homework;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.HomeworkMapper;
import com.zhangxiang.ccims.mapper.HomeworkSubmitMapper;
import com.zhangxiang.ccims.util.FileUtil;
import com.zhangxiang.ccims.util.MybatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/homeworks")
public class HomeworkController {

    @Resource
    private HomeworkMapper HomeworkMapper;
    @Resource
    private HomeworkSubmitMapper homeworkSubmitMapper;

    @Resource
    private FileUtil fileUtil;

    @PostMapping("")
    public Result add(Homework homework,@RequestParam(value = "files",required = false) MultipartFile[] files) throws IOException {
        System.out.println(homework);
        System.out.println();
        homework.setCreate_date(new Date());
        int i = HomeworkMapper.insert(homework);
        System.out.println(homework.getH_id());
        if (files != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            StringJoiner stringJoiner = new StringJoiner(";");
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    System.out.println(originalFilename);
                   // file.transferTo(new File("E:\\nihao\\" + originalFilename)); //这里的路径电脑上必须有
                    Path path = Paths.get(fileUtil.getHomework_path(), String.valueOf(homework.getH_id()),
                            localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),file.getOriginalFilename());
                    if(!Files.exists(path.getParent())){
                        Path path1 = Files.createDirectories(path.getParent());
                        System.out.println(path1.getFileSystem());
                        System.out.println(path1);
                    }
                    file.transferTo(path);
                    stringJoiner.add(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+path.toString());

                }
            }
            homework.setFile_paths(stringJoiner.toString());
            HomeworkMapper.updateById(homework);
        }

        return MybatisUtil.getResult(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = HomeworkMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PostMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody Homework Homework) {
        Homework.setH_id(id);
        int i = HomeworkMapper.updateById(Homework);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        Homework Homework = HomeworkMapper.selectById(id);
        return Result.builder().msg(Homework).code(Code.SUCCESS.getCode()).build();
    }
    @GetMapping("")
    public Result getAll(@RequestParam(required = false) String type,@RequestParam(required = false) Integer id,HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("user"));
        if (type == null){
            List<Homework> Homeworks = HomeworkMapper.selectList(Wrappers.emptyWrapper());
            return Result.builder().msg(Homeworks).code(Code.SUCCESS.getCode()).build();
        }

        if (type.equals("ca")){
            if (id == null){
                return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
            }
            List<Homework> Homeworks = HomeworkMapper.listHomeworkByCa_id(id);
            List<Homework> collect = Homeworks.stream().map((homework) -> {
                if (homeworkSubmitMapper.selectAllByS_idAndH_idA(1,homework.getH_id()).size()>0) {
                    homework.setSubmit(true);
                }else{
                    homework.setSubmit(false);
                }
                return homework;
            }).collect(Collectors.toList());
            return Result.builder().msg(Homeworks).code(Code.SUCCESS.getCode()).build();
        }else{
            List<Homework> Homeworks = HomeworkMapper.selectList(Wrappers.emptyWrapper());
            return Result.builder().msg(Homeworks).code(Code.SUCCESS.getCode()).build();
        }

    }


    @GetMapping("/{h_id}/file_list")
    public Result getFileList(@PathVariable Integer h_id){
        // 根据h_id获取附件列表
        Path path = Paths.get(fileUtil.getHomework_path(), String.valueOf(h_id));
        File file = path.toFile();
        List<Map> files= new ArrayList<>();
        Homework homework = HomeworkMapper.selectById(h_id);
        if (homework.getFile_paths() == null || homework.getFile_paths().isBlank()) {
            return Result.builder().code(Code.SUCCESS.getCode()).msg(new Integer[]{}).build();
        }
        String[] split = homework.getFile_paths().split(";");
        sort(split);
        List<File> list = new ArrayList<>();
        listAllFile(path.toFile(),list);
        int index = 0;
        for (int i = 0; i < split.length; i++) {
             for (File file1 : list) {
                if (split[i].substring(19).equals(file1.getAbsolutePath())) {
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

    @GetMapping("/{h_id}/download")
    public Result download(@PathVariable Integer h_id, @RequestParam String file_name, HttpServletResponse response) throws IOException {
        Homework homework = HomeworkMapper.selectById(h_id);
        if (homework.getFile_paths() == null) {
            return Result.builder().code(Code.FAIL.getCode()).msg("数据库中没有记录！").build();
        }
        String[] split = homework.getFile_paths().split(";");
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

//    public Result deleteFile(@PathVariable Integer h_id){
//        return null;
//    }
//
//    public Result uploadFile(@PathVariable Integer h_id, @RequestParam("file") MultipartFile multipartFile){
//        return null;
//    }
//
//    public Result listFile(@PathVariable Integer h_id){
//        return null;
//    }

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
