package com.zjy.phoenix.module.admin.controller;


import com.zjy.phoenix.common.base.BaseController;
import com.zjy.phoenix.common.util.poi.ExportExcelByAnno;
import com.zjy.phoenix.common.util.poi.ExportExcelByReflect;
import com.zjy.phoenix.common.util.poi.ImportExcelByAnno;
import com.zjy.phoenix.config.result.Result;
import com.zjy.phoenix.module.admin.model.ExcelVO;
import com.zjy.phoenix.module.admin.model.WFile;
import com.zjy.phoenix.module.admin.service.WFileService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: poi 测试模块
* @Author: ZhangJianYong
* @Date: 19/4/12
*/
@Controller
@RequestMapping("/poi")
public class POIController extends BaseController {

    @RequestMapping("list")
    public String list() {
        return "poi/list";
    }
    @Autowired
    WFileService wfileService;
    @RequestMapping("/exportExcel")
    public boolean exportExcel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {


        String officeName = "exportExcel";
        String   filename1=officeName+".xls";
        String filename= new String(filename1.getBytes(),"iso-8859-1");//中文文件名必须使用此句话
        response.setContentType("application/DOWLOAD");
        response.setContentType("application/OCTET-STREAM;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+filename );
        String[] headers = { "id","任务", "单位","子节点","总部门" };  //表格的标题栏
        List<WFile> list = wfileService.selectAll();
        //WorkloadExport 为将要导出的类，也就是表格的一行记录，里面的所有字段都不能为空，必须生成set get方法
        //导出列顺序和类中成员顺序一致
        try {
            ExportExcelByReflect<WFile> ex = new ExportExcelByReflect<WFile>();  //构造导出类

            OutputStream out = new BufferedOutputStream(response.getOutputStream());

            //String  title = list.get(0).getOffice();  //title需要自己指定 比如写Sheet

            ex.exportExcel("allFiles",headers, list, out);  //title是excel表中底部显示的表格名，如Sheet
            out.close();
            //JOptionPane.showMessageDialog(null, "导出成功!");
            //System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  true;

    }



    @RequestMapping("/exportExcelByAnno")
    public boolean exportExcelByAnno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> headerList = new ArrayList();
		for (int i = 1; i <= 10; i++) {
			headerList.add("表头"+i);
		}

		List<String> dataRowList = new ArrayList();
		for (int i = 1; i <= headerList.size(); i++) {
			dataRowList.add("数据"+i);
		}

		List<List<String>> dataList = new ArrayList();
		for (int i = 1; i <=1000000; i++) {
			dataList.add(dataRowList);
		}

        ExportExcelByAnno ee = new ExportExcelByAnno("表格标题", headerList);

		for (int i = 0; i < dataList.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < dataList.get(i).size(); j++) {
				ee.addCell(row, j, dataList.get(i).get(j));
			}
		}

		ee.writeFile("target/export.xlsx");

		ee.dispose();

		log.debug("Export success.");

        return  true;
    }


    @RequestMapping("/exportExcelByAnnoByVO")
    public void exportExcelByAnnoByVO(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<WFile> list = wfileService.selectAll();
        List<ExcelVO> excelVOlist = new ArrayList<ExcelVO>();
        for (WFile wFile : list) {
            excelVOlist.add(new ExcelVO(wFile.getFileName(),wFile.getFilePath(),wFile.getFileSize(),wFile.getIntroduce(),wFile.getUploadName()));
        }
        List<String> headerList = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            headerList.add("表头"+i);
        }
        ExportExcelByAnno ee = new ExportExcelByAnno("表格标题",ExcelVO.class);


        ee.setDataList(excelVOlist);
        ee.write(response,"ee.xlsx");
        ee.dispose();
        log.debug("Export success.");
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public Result exportExcelByAnnoByVO(@RequestParam("uploadFile")MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
        Result result = new Result();
        ImportExcelByAnno ei = new ImportExcelByAnno(uploadFile,1,0);
        List<ExcelVO> dataList = ei.getDataList(ExcelVO.class, null);
        List<WFile> wfilelist = new ArrayList<WFile>();
        for (ExcelVO excelVO : dataList) {
            wfilelist.add(new WFile(excelVO.getFileName(),excelVO.getFilePath(),excelVO.getFileSize(),excelVO.getIntroduce(),excelVO.getUploadName()));
        }


        wfileService.insertBatch(wfilelist);
        return result;
    }
}
