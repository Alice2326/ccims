package com.zhangxiang.ccims.util;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class FileUtil {

    @Value("${my_config.material.path}")
    private String material_path;

    @Value("${my_config.homework.path}")
    private String homework_path;

    public void saveCCIMSFile(String type, String id, String s_id, MultipartFile[] multipartFiles){
        System.out.println(material_path);
        System.out.println(homework_path);

    }
    public void saveCCIMSFile(){
        System.out.println(material_path);
        System.out.println(homework_path);

    }


}
