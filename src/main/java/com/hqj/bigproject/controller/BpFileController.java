package com.hqj.bigproject.controller;

import com.github.pagehelper.PageInfo;
import com.hqj.bigproject.pojo.BpFile;
import com.hqj.bigproject.service.BpFileService;
import com.hqj.bigproject.utils.DowloadFiles;
import com.hqj.bigproject.utils.ImportFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/file")
public class BpFileController {
    private static final Logger LOG = LoggerFactory.getLogger(BpFileController.class);

    @Autowired
    private BpFileService bpFileService;

    private static final String FILEPATH = "D:/work/code/bigprojectTour/src/main/resources/static/uploadFiles";

    /**
     * 查看文件
     * @return
     */
    @RequestMapping(value = "/viewFiles")
    public String queryFiles(ModelMap modelMap,@RequestParam Integer pageNum){
        if (pageNum == null || "".equals(pageNum)) {
            pageNum = 1;
        }
        PageInfo<BpFile> pageInfo = bpFileService.queryFilsAll(pageNum,3);
        modelMap.addAttribute("bpfileList", pageInfo);
        return "thymeleaf/file/files";
    }

    /**
     * 上传单个文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/importfile", method = RequestMethod.POST)
    @ResponseBody
    public String importFile(@RequestParam("file") MultipartFile file) {
        String status = ImportFiles.importFile(FILEPATH, file);
        if ("ok".equals(status)){
            bpFileService.insertBpFile(file.getOriginalFilename(), file.getOriginalFilename(), FILEPATH, "hqj", (int) file.getSize());
        }
        return status;
    }

    /**
     * 上传多个文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/importfiles", method = RequestMethod.POST)
    @ResponseBody
    public String importFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String status = ImportFiles.importFiles(FILEPATH, files);
        if ("ok".equals(status)){
            for (MultipartFile file : files) {
                bpFileService.insertBpFile(file.getOriginalFilename(), file.getOriginalFilename(), FILEPATH, "hqj", (int) file.getSize());
            }
        }
        return status;
    }

    /**
     * 下载单个文件
     * @param fileId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dowloadFile", method = RequestMethod.GET)
    @ResponseBody
    public String dowloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) {
        if (fileId == null) {
            return "fail";
        }
        BpFile bpFile = bpFileService.selectBpFile(fileId);
        String status = DowloadFiles.dowloadFile(bpFile.getFileName(), FILEPATH,response,request);
        if ("ok".equals(status)){
            bpFileService.updateBpFile(fileId,"hqj");
        }
        return status;

    }

    /**
     * 批量下载文件
     * @param fileIds
     * @param response
     * @return
     */
    @RequestMapping(value = "/dowloadFiles",method = RequestMethod.GET)
    public String dowloadFiles(String fileIds,HttpServletResponse response){
        if (fileIds == null) {
            return "文件下载失败";
        }
        String[] ids = fileIds.split(",");
        String[] fileNames = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BpFile bpFile = bpFileService.selectBpFile(ids[i]);
            if (bpFile != null) {
                fileNames[i] = bpFile.getFileName();
            }
        }
        //DowloadFiles.dowloadFiles(FILEPATH,response,fileNames);//第一种批量下载方法
        DowloadFiles.doDowloadFiles(FILEPATH,fileNames,response);//第二种批量下载方法
        //DowloadFiles.fileBatchDownload(fileNames,FILEPATH,response);//第三种批量下载方法

        for (int i = 0; i < ids.length; i++) {
            bpFileService.updateBpFile(ids[i],"hqj");
        }
        return null;
    }

    /**
     * 删除文件
     * @return
     */
    @RequestMapping(value = "/doDeleteFile",method = RequestMethod.GET)
    @ResponseBody
    //@CrossOrigin Ajax跨域请求
    public String doDeleteFile(String fileId){
        String status = null;
        BpFile bpFile = bpFileService.selectBpFile(fileId);
        String fileName = bpFile.getFileName();
        int num = bpFileService.deleteFile(fileId);
        if(num > 0){
            DowloadFiles.deleteFile(fileName, FILEPATH);
            status = "ok";
        }else{
            status = "fail";
        }

        return status;
    }
}
