package com.zjy.phoenix.module.admin.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zjy.phoenix.common.annotation.LoginRequired;
import com.zjy.phoenix.common.base.BaseController;
import com.zjy.phoenix.common.util.FileUtil;
import com.zjy.phoenix.config.result.Result;
import com.zjy.phoenix.module.admin.model.WFile;
import com.zjy.phoenix.module.admin.service.WFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
* @Description: 文件上传下载模块
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Controller
@RequestMapping("/file")
public class WFileController extends BaseController {

    @Autowired
    WFileService thisService;

    @LoginRequired
    @RequestMapping("list")
    public String list() {
        return "file/list";
    }


    @ResponseBody
    @RequestMapping("/getData")
    public Result getData(Map<String, Object> map, @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "15") Integer pageSize, String name) {
        Result result = new Result();

        Page<WFile> page = new Page<WFile>(pageNum, pageSize);
        try {
            page = thisService.getPage(page, name);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result.setData(page);
        return result;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Result findById(Long id) {
        Result result = new Result();
        WFile model = thisService.selectById(id);
        result.setData(model);
        return result;
    }

    @ResponseBody
    @RequestMapping("/addoredit")
    public Result addOrEdit(Map<String, Object> map, WFile model, HttpServletRequest request) {
        Result result = new Result();
        thisService.addOrEdit(model);
        return result;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Result del(Map<String, Object> map, Long id) {
        Result result = new Result();
        throw new RuntimeException("删除异常");
        //thisService.del(id);
       // return result;
    }



    @ResponseBody
    @RequestMapping("/uploadFile")
    public Result uploadFile(WFile model ,
                             @RequestParam("uploadFile")MultipartFile uploadFile,
                             HttpServletRequest request) {
        Result result = new Result();
        result = FileUtil.uploadFile(request,uploadFile);
        Long id =model.getId();
        //修改
        if(id!=null){

            if(uploadFile == null){
                //只修改文件名和介绍
                result = addOrEdit(new HashMap<String, Object>(), model, request);
                return result;
            }else{
                //修改了文件 删除记录
                thisService.del(id);
            }
        }
        //添加
        WFile model1 = new WFile();
        if(result.isSuccess()){
            model1.setFileName(model.getFileName() );
            model1.setIntroduce(model.getIntroduce());
            model1.setFilePath((String) result.get("uploadPath"));
            model1.setUploadName((String) result.get("uploadName"));
            model1.setFileSize(FileUtil.getFileSize(uploadFile));
            result = addOrEdit(new HashMap<String, Object>(), model1, request);
        }
        return result;
    }





    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("id")Long  id, HttpServletRequest request, HttpServletResponse response){
        Result result = findById(id);
        WFile wFile = (WFile) result.getData();

        String  filePath = wFile.getFilePath() + wFile.getUploadName();
        String  fileName = wFile.getFileName();

        ResponseEntity<byte[]> responseEntity = null;
        try {
            responseEntity = FileUtil.downFileBackByte(request, filePath, fileName);
        } catch (Exception e) {
            log.info("下载错误");
        }
        return responseEntity;

    }

    @RequestMapping("/downloadFileInput")
    public ResponseEntity<InputStreamResource> downloadFileInput(@RequestParam("id")Long  id, HttpServletRequest request, HttpServletResponse response){
        Result result = findById(id);
        WFile wFile = (WFile) result.getData();

        String  filePath = wFile.getFilePath() + wFile.getUploadName();
        String  fileName = wFile.getFileName();

        ResponseEntity<InputStreamResource> responseEntity = null;
        try {
            responseEntity = FileUtil.downFile(filePath, fileName);
        } catch (Exception e) {
            log.info("下载错误");
        }
        if(responseEntity == null){
            throw  new  RuntimeException("下载错误");
        }
        return responseEntity;

    }

}
