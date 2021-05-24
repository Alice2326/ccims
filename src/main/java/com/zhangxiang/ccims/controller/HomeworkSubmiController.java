package com.zhangxiang.ccims.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhangxiang.ccims.common.Result;
import com.zhangxiang.ccims.entity.Homework;
import com.zhangxiang.ccims.entity.HomeworkSubmit;
import com.zhangxiang.ccims.enums.Code;
import com.zhangxiang.ccims.mapper.HomeworkSubmitMapper;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/homework_submits")
public class HomeworkSubmiController {

    @Resource
    private HomeworkSubmitMapper HomeworkSubmitMapper;

    @Resource
    private FileUtil fileUtil;


    @PostMapping("")
    public Result add(HomeworkSubmit homeworkSubmit, @RequestParam(value = "files",required = false) MultipartFile[] files) throws IOException {

        homeworkSubmit.setSubmit_time(new Date());
        homeworkSubmit.setS_id(1);
        int i = HomeworkSubmitMapper.insert(homeworkSubmit);
        System.out.println(homeworkSubmit.getHs_id());
        if (files != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            StringJoiner stringJoiner = new StringJoiner(";");
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    System.out.println(originalFilename);
                    // file.transferTo(new File("E:\\nihao\\" + originalFilename)); //这里的路径电脑上必须有
                    Path path = Paths.get(fileUtil.getHomework_path(),
                            String.valueOf(homeworkSubmit.getH_id()),
                            "submit",
                            String.valueOf(homeworkSubmit.getHs_id()),
                            localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            localDateTime.format(DateTimeFormatter.ofPattern("HH-mm-ss")),file.getOriginalFilename());
                    if(!Files.exists(path.getParent())){
                        Path path1 = Files.createDirectories(path.getParent());
                        System.out.println(path1.getFileSystem());
                        System.out.println(path1);
                    }
                    file.transferTo(path);
                    stringJoiner.add(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))+path.toString());
                }
            }

            homeworkSubmit.setFile_paths(stringJoiner.toString());
            HomeworkSubmitMapper.updateById(homeworkSubmit);
        }

        return MybatisUtil.getResult(i);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        int i = HomeworkSubmitMapper.deleteById(id);
        return MybatisUtil.getResult(i);
    }

    @PostMapping("/{id}")
    public Result update(@PathVariable Integer id,@RequestBody HomeworkSubmit HomeworkSubmit) {
        HomeworkSubmit.setHs_id(id);
        int i = HomeworkSubmitMapper.updateById(HomeworkSubmit);
        return MybatisUtil.getResult(i);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        HomeworkSubmit HomeworkSubmit = HomeworkSubmitMapper.selectById(id);
        return Result.builder().msg(HomeworkSubmit).code(Code.SUCCESS.getCode()).build();
    }
    @GetMapping("")
    public Result getAll(@RequestParam(required = false) String type,@RequestParam(required = false) Integer id) {
        if (type == null){
            List<HomeworkSubmit> homeworkSubmits = HomeworkSubmitMapper.selectList(Wrappers.emptyWrapper());
            return Result.builder().msg(homeworkSubmits).code(Code.SUCCESS.getCode()).build();
        }
        if (id == null){
            return Result.builder().msg("id不能为空").code(Code.FAIL.getCode()).build();
        }
        List<HomeworkSubmit> homeworkSubmits = HomeworkSubmitMapper.selectList(Wrappers.emptyWrapper());

        if (type.equals("h")){
            return Result.builder().msg(
                    homeworkSubmits.stream().filter((homeworkSubmit)-> homeworkSubmit.getH_id() == id).collect(Collectors.toList())
            ).code(Code.SUCCESS.getCode()).build();

        }
        if (type.equals("hs")){
            Integer s_id = 1;

            return Result.builder().msg(
                    homeworkSubmits.stream().filter((homeworkSubmit)-> homeworkSubmit.getS_id() == s_id && homeworkSubmit.getH_id() == id).collect(Collectors.toList())
            ).code(Code.SUCCESS.getCode()).build();

        }

        return Result.builder().msg(
                homeworkSubmits
        ).code(Code.SUCCESS.getCode()).build();

    }

    @GetMapping("/{h_id}/submit/{hs_id}/file_list")
    public Result getFileList(@PathVariable Integer h_id,@PathVariable Integer hs_id){
        // 根据h_id获取附件列表
        Path path = Paths.get(fileUtil.getHomework_path(), String.valueOf(h_id),"submit",String.valueOf(hs_id));
        File file = path.toFile();
        List<String> files= new ArrayList<>();
        HomeworkSubmit homeworkSubmit = HomeworkSubmitMapper.selectById(h_id);
        if (homeworkSubmit.getFile_paths() == null) {
            return Result.builder().code(Code.FAIL.getCode()).msg("数据库中没有记录！").build();
        }
        String[] split = homeworkSubmit.getFile_paths().split(";");

        List<File> list = new ArrayList<>();
        listAllFile(path.toFile(),list);
        for (File file1 : list) {
            if (Arrays.stream(split).anyMatch(s -> s.equals(file1.getAbsolutePath()))) {
                files.add(file1.getName());
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

    @GetMapping("/{h_id}/submit/{hs_id}/download")
    public Result download(@PathVariable Integer h_id, @PathVariable Integer hs_id,@RequestParam String file_name, HttpServletResponse response) throws IOException {
        HomeworkSubmit homework = HomeworkSubmitMapper.selectById(hs_id);
        if (homework.getFile_paths() == null) {
            return Result.builder().code(Code.FAIL.getCode()).msg("数据库中没有记录！").build();
        }
        String[] split = homework.getFile_paths().split(";");
        System.out.println(split);

        boolean flag = false;
        for (String s : split) {
            File file = new File(s);
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

//    @GetMapping("/{id}/download")
//    public Result download(@PathVariable Integer hs_id){
//        // 下载附件
//        return null;
//
//
//
//    }
}
