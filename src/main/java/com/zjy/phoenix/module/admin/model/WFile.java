package com.zjy.phoenix.module.admin.model;


import com.baomidou.mybatisplus.annotations.TableName;
import com.zjy.phoenix.common.base.BaseModel;


@TableName("t_file")
public class WFile extends BaseModel {
    private String fileName;
    private String filePath;
    private String fileSize;
    private String introduce;
    private String uploadName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public WFile(String fileName, String filePath, String fileSize, String introduce, String uploadName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.introduce = introduce;
        this.uploadName = uploadName;
    }

    public WFile() {
    }
}
