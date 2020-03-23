package com.hqj.bigproject.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImportFiles {

    /**
     * 上传单个文件
     * @param filePath
     * @param file
     * @return
     */
    public static String importFile(String filePath,MultipartFile file){
        Boolean falg = false;
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            long size = file.getSize();
            File importFile = new File(filePath+"/"+fileName);
            if (!importFile.getParentFile().exists()){
                importFile.getParentFile().mkdir();
            }
            try {
                file.transferTo(importFile);
                return "ok";
            }catch (IOException e){
                e.printStackTrace();
                return "fail";
            }
        }
        return "ok";
    }

    /**
     * 同时上传多个文件
     * @param filePath
     * @param files
     * @return
     */
    public static String importFiles(String filePath, List<MultipartFile> files){
        if (files.isEmpty()){
            return "请选择文件！";
        }
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();
            File dest = new File(filePath +"/"+ fileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                return "上传第" + (i++) + "个文件失败";
            }
        }
        return "ok";
    }
}
