package com.hqj.bigproject.utils;

import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DowloadFiles {

    /**
     * 单个文件下载
     *
     * @param fileName
     * @param filePath
     * @param response
     * @return
     */
    public static String dowloadFile(String fileName, String filePath, HttpServletResponse response) {
        File file = new File(filePath + File.separator + fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                outputStream.flush();
                return "ok";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "fail";
    }

    /**
     * 单个文件下载
     * @param fileName
     * @param filePath
     * @param response
     * @param request
     * @return
     */
    public static String dowloadFile(String fileName,String filePath,HttpServletResponse response, HttpServletRequest request){
        //获取文件的绝对路径
        //String realPath = request.getSession().getServletContext().getRealPath("upload");
        File file = new File(filePath + File.separator + fileName);
        if (file.exists()){
            try {
                //获取输入流对象（用于读文件）
                FileInputStream fis = new FileInputStream(file);
                //获取文件后缀（.txt）
                String extendFileName = fileName.substring(fileName.lastIndexOf('.'));
                //动态设置响应类型，根据前台传递文件类型设置响应类型
                response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
                try {
                    //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
                    response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
                    try {
                        //获取输出流对象（用于写文件）
                        ServletOutputStream os = response.getOutputStream();
                        //下载文件,使用spring框架中的FileCopyUtils工具
                        FileCopyUtils.copy(fis,os);
                        return "ok";
                    }catch (IOException e){
                        e.printStackTrace();
                        return "fail";
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                    return "fail";
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
                return "fail";
            }
        }
        return "fail";
    }

    /**
     * 批量下载文件（有问题）
     * 问题已解决（原因是因为下载方法有返回值）
     * @param filePath
     * @param response
     * @param names
     * @return
     */
    public static void dowloadFiles(String filePath,HttpServletResponse response,String [] names){
        //存放--服务器上zip文件的目录
        File directoryFile = new File(filePath);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }
        //设置压缩包的名字
        String dates = System.currentTimeMillis() + "";
        //设置最终输出zip文件的目录+文件名
        String zipFileName = "file-"+dates+".zip";
        String strZipPath = filePath+File.separator+zipFileName;
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try{
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i<names.length ;i++){
                //解码获取真实路径与文件名
                String realFileName = java.net.URLDecoder.decode(names[i],"UTF-8");
                String realFilePath = java.net.URLDecoder.decode(filePath+File.separator+names[i],"UTF-8");
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if(file.exists()){
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */
                    ZipEntry zipEntry = new ZipEntry(realFileName);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
                    {
                        zipStream.write(buf, 0, read);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream){
                    zipStream.flush();
                    zipStream.close();
                }
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            String status = dowloadFile(zipFileName,filePath,response);
            if ("ok".equals(status)){
                zipFile.delete();
            }
        }
    }

    /**
     * 多文件下载（有问题）
     * @param filePath
     * @param fileNames
     * @param response
     * @return
     */
    public static void doDowloadFiles(String filePath,String[] fileNames,HttpServletResponse response) {
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        //设置压缩包的名字
        String dates = System.currentTimeMillis() + "";
        String billname = "file-" + dates;
        String downloadName = billname + ".zip";
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");
        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); //设置压缩方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        //循环将文件写入压缩流
        DataOutputStream os = null;
        //遍历文件信息（主要获取文件名/文件路径等）
        for (int i = 0; i < fileNames.length; i++) {
            String fileExtName = fileNames[i].substring(fileNames[i].lastIndexOf(".") + 1);
            File file = new File(filePath + File.separator + fileNames[i]);
            if (file.exists()) {
                {
                    try {
                        //添加ZipEntry，并ZipEntry中写入文件流
                        zipos.putNextEntry(new ZipEntry(fileNames[i]));
                        os = new DataOutputStream(zipos);
                        InputStream is = new FileInputStream(file);
                        byte[] b = new byte[1024];
                        int length = 0;
                        while ((length = is.read(b)) != -1) {
                            os.write(b, 0, length);
                        }
                        is.close();
                        zipos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //关闭流
        try {
            os.flush();
            os.close();
            zipos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多文件下载（有问题）
     * 问题已解决（原因是因为下载方法有返回值）
     * @param fileNames
     * @param filePath
     * @param response
     * @return
     */
    public static void fileBatchDownload(String[] fileNames, String filePath, HttpServletResponse response) {
        try {
            File tempFile = File.createTempFile("tempFile", "zip", new File(filePath));
            tempFile = putBatchFilesInZip(fileNames, filePath, tempFile, response);
            //下载头设置。
            //response.setContentType("application/force-download");
            try (OutputStream os = response.getOutputStream();
                 FileInputStream fis = new FileInputStream(tempFile)) {
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("压缩包.zip", "UTF-8"));
                int len = 0;
                byte[] bt = new byte[5 * 1024];
                while ((len = fis.read(bt)) != -1) {
                    os.write(bt, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                tempFile.deleteOnExit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 方法说明将files打包放到一个zip中。
     *
     * @return
     * @throws IOException
     */
    private static File putBatchFilesInZip(String[] fileNames, String filePath, File tempFile, HttpServletResponse response) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
        for (int i = 0; i < fileNames.length; i++) {
            File inputFile = new File(filePath + File.separator + fileNames[i]);
            try (FileInputStream fis = new FileInputStream(inputFile);) {
                //压缩文件中写入名称
                ZipEntry entry = new ZipEntry(fileNames[i]);
                zos.putNextEntry(entry);
                // 向压缩文件中输出数据
                int len = 0;
                byte[] bt = new byte[5 * 1024];
                while ((len = fis.read(bt)) != -1) {
                    //压缩文件中写入真正的文件流
                    zos.write(bt, 0, len);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }
        zos.flush();
        zos.close();
        return tempFile;
    }

    /**
     * 删除文件
     * @param fileName
     * @param filePath
     */
    public static void deleteFile(String fileName,String filePath){
        filePath += File.separator+fileName;
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

}
