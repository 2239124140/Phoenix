package com.zjy.phoenix.module.admin.model;


import com.zjy.phoenix.common.util.poi.ExcelField;

public class ExcelVO {
    @ExcelField(title="文件名")
    private String fileName;
    @ExcelField(title="文件路径")
    private String filePath;
    @ExcelField(title="文件大小")
    private String fileSize;
    @ExcelField(title="文件介绍")
    private String introduce;
    @ExcelField(title="上传文件名")
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

    public ExcelVO(String fileName, String filePath, String fileSize, String introduce, String uploadName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.introduce = introduce;
        this.uploadName = uploadName;
    }

    public ExcelVO() {
    }
}
