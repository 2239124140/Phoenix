package com.zjy.phoenix.common.util;

import com.alibaba.fastjson.JSONObject;

import com.zjy.phoenix.config.result.Result;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/** 
* @Description: 文件上传工具类 
* @Author: ZhangJianYong 
* @Date: 19/4/12 
*/ 
@Component
public class FileUtil {
	
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	
	// private static UploadConfig config =
	// SpringContextHolder.getBean(UploadConfig.class);;


    /** 
    * @Description:  判断文件是否存在
    * @param： fileName  文件名 
    * @return:  Boolean
    * @Author: ZhangJianYong 
    * @Date: 19/4/12 
    */ 
    public static Boolean  isExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }

    
    /** 
    * @Description: 单个文件上传 uploadPath+filePath+tmpName File.separator windows上为\ linux上为 / 
    * @param： null 
    * @return:  
    * @Author: ZhangJianYong 
    * @Date: 19/4/12 
    */ 
	public static Result uploadFile(HttpServletRequest request, MultipartFile file) {
        Result result = new Result();
        // 限制文件大小
        long maxSize = 1024 * 1024 * 10L;  //限制单文件10M
        if (maxSize < file.getSize()) {
            result.error("上传文件不能大于" + getFileSize(maxSize));
            return result;
        }
        //上传文件根路径
		String rootPath = request.getServletContext().getRealPath("/");

        //上传文件路径目录
        String datePath = DateUtil.getDateStr("yyyyMMdd");
        //  /static/upload/yyyyMMdd
		String subPath = new StringBuilder(File.separator).append("static").append(File.separator).append("upload").append(File.separator).append(datePath).append(File.separator).toString(); // /upload/yyyyMMdd/
        String uploadPath = rootPath + subPath.substring(1);
        File folder = new File(rootPath + subPath); //  path 为配置文件中 uploadPath=/opt/app/tomcats/qxgl/one/webapps/ROOT/
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //上传文件名字
        //rand.nextInt(900000)代表 0<=n<900000 加100000后 0<=n<1000000  效果就是生成随机的6位数
        Random rand = new Random();
        // yyyyMMddHHmmss1000000.text
        String uploadName = DateUtil.getDateStr("yyyyMMddHHmmss") + (rand.nextInt(900000) + 100000) + "." + getExtensionName(file.getOriginalFilename());
        String savePath = String.format("%s%s", uploadPath, uploadName);
        try {
            file.transferTo(new File(savePath));
        } catch (Exception e) {
        	log.error(StrKit.printException(e));
            result.error(file.getOriginalFilename() + "上传失败");
            return result;
        }
        //返回文件路径
        result.put("uploadName", uploadName);
        result.put("uploadPath", uploadPath);
        return result;
    }


    /** 
    * @Description:  多文件文件上传
    * @param： null 
    * @return:  result
    * @Author: ZhangJianYong 
    * @Date: 19/4/12 
    */
    public static Result uploadFile(HttpServletRequest request, MultipartFile[] files) {
        Result result = new Result();
        // 限制文件大小
        long maxSize = 1024 * 1024 * 10L;  //限制单文件10M
        long maxRequestSize = 1024 * 1024 * 10 *10L;  //限制单文件10M
        long countSize = 0L;
        for (MultipartFile multipartFile : files) {
            if (maxSize < multipartFile.getSize()) {
                result.error("上传文件不能大于" + getFileSize(maxSize));
                return result;
            }
            countSize += multipartFile.getSize();
            if (countSize > maxRequestSize) {
                result.error("上总文件不能大于" + getFileSize(maxRequestSize));
                return result;
            }
        }

        String rootPath = request.getServletContext().getRealPath("/");
        
        String datePath = DateUtil.getDateStr("yyyyMMdd");
        //  /static/upload/yyyyMMdd
        String subPath = new StringBuilder(File.separator).append("static").append(File.separator).append("upload").append(File.separator).append(datePath).append(File.separator).toString(); // /upload/yyyyMMdd/
        String uploadPath = rootPath + subPath.substring(1);
        File folder = new File(rootPath + subPath); //  path 为配置文件中 uploadPath=/opt/app/tomcats/qxgl/one/webapps/ROOT/
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //循环存储
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for (MultipartFile file : files) {
            HashMap<String,String> map = new HashMap<String,String>();
            //rand.nextInt(900000)代表 0<=n<900000 加100000后 0<=n<1000000  效果就是生成随机的6位数
            Random rand = new Random();
            // yyyyMMddHHmmss1000000.text
            String uploadName = DateUtil.getDateStr("yyyyMMddHHmmss") + (rand.nextInt(900000) + 100000) + "." + getExtensionName(file.getOriginalFilename());
            String savePath = String.format("%s%s", uploadPath, uploadName);
            map.put("uploadName",uploadName);
            map.put("uploadPath",uploadPath);
            list.add(map);
            try {
                file.transferTo(new File(savePath));
            } catch (Exception e) {
                log.error(StrKit.printException(e));
                result.error(file.getOriginalFilename() + "上传失败");
                return result;
            }finally {
                //上传失败后 尽量删除
                for (HashMap map1 : list) {
                try {
                        String delfile = (String) map1.get("uploadName") + map1.get("savePath");
                        FileUtils.deleteQuietly(new File("delfile")) ;
                }catch (Exception e){
                    log.error(StrKit.printException(e));
                }
                }
            }
        }
        //返回文件路径
        result.setData(new JSONObject().put("file",list));
        return result;
    }

    /** 
    * @Description: 根据 文件存储路径+文件名 下载  默认下载的文件名是传过来的文件名
    * @param： null 
    * @return:  
    * @Author: ZhangJianYong 
    * @Date: 19/4/12 
    */ 
    public static ResponseEntity<InputStreamResource> downFile(String filePath){
        FileSystemResource file = new FileSystemResource(filePath);
        return downFile(filePath,file.getFilename());
    }

    /**
     * @Description: 根据 文件存储路径+文件名 下载  默认下载的文件名是自定义文件名下载
     * @param： null
     * @return: ResponseEntity<InputStreamResource>
     * @Author: ZhangJianYong
     * @Date: 19/4/12
     */
    public static ResponseEntity<InputStreamResource> downFile (String filePath, String fileName){
        if(!isExist(filePath)){
            return null;
        }

        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName+"."+getExtensionName(file.getFilename())));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType
                            .parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @Description: 根据 文件存储路径+文件名 下载  默认下载的文件名是自定义文件名下载
     * @param：
     * @return: ResponseEntity<byte[]>
     * @Author: ZhangJianYong
     * @Date: 19/4/12
     */
    public static ResponseEntity<byte[]> downFileBackByte (HttpServletRequest request, String filePath, String fileName)throws Exception{

        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName+"."+getExtensionName(file.getFilename())));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        //2. 直接获取到下载的文件的输入流
        ServletContext servletContext = request.getServletContext();
       // InputStream in = servletContext.getResourceAsStream(filePath);
        InputStream in = new FileInputStream(filePath);
        //3. 将文件读取到字节数组中
        byte [] imageBytes =  new byte[in.available()] ;//in.available() 输入流最大字节数
        in.read(imageBytes);
        try {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType
                            .parseMediaType("application/octet-stream"))
                    .body(imageBytes);
        } catch (IOException e) {
            return null;
        }
    }


    /**
    * @Description: 获取对应字节数文件大小
    * @param： null
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    public static String getFileSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以MB为单位的话，要保留最后1位小数，
            // 因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }


    /**
    * @Description: 获取文件大小
    * @param： null
    * @return:
    * @Author: ZhangJianYong
    * @Date: 19/4/12
    */
    public static String getFileSize(File file) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if(file ==null){
            return 0 + "B";
        }
        return getFileSize(file.length());
    }

    /**
     * @Description: 获取文件大小
     * @param： null
     * @return:
     * @Author: ZhangJianYong
     * @Date: 19/4/12
     */
    public static String getFileSize(MultipartFile uploadFile) {
        if(uploadFile ==null){
            return 0 + "B";
        }
        return getFileSize(uploadFile.getSize());
    }

    /**
     * @Description: 获取文件扩展名
     * @param： null
     * @return:
     * @Author: ZhangJianYong
     * @Date: 19/4/12
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


}
