package com.zjy.phoenix;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class POITest {

    private Logger logger = LoggerFactory.getLogger(POITest.class);

    //自创建excl
    @Test
    public void testCreate() throws Exception{

        // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 在Excel工作簿中建一工作表，其名为缺省值
        //HSSFSheet sheet = workbook.createSheet();

        // 如要新建一名为"效益指标"的工作表，其语句为：
        HSSFSheet sheet = workbook.createSheet("会员登录统计");

        // 创建行（第一行）
        HSSFRow row1 = sheet.createRow(0);

        // 创建单元格（第一列）
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("今日人数");

        // 创建单元格（第二列）
        HSSFCell cell2 = row1.createCell(1);
        cell2.setCellValue(666);

        //创建单元格（第三列）
        HSSFCell cell3 = row1.createCell(2);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cell3.setCellValue(sdf.format(date));

        // 新建一输出文件流（注意：要先创建文件夹）
        FileOutputStream out = new FileOutputStream("H:/poi-excel/test-write.xls");
        // 把相应的Excel 工作簿存盘
        workbook.write(out);
        out.flush();
        // 操作结束，关闭文件
        out.close();
        System.out.println("文件生成成功");
    }


    @Test
    public void testRead() throws Exception {

        InputStream is = new FileInputStream("H:/poi-excel/商品表.xls");

        //InputStream is = new FileInputStream("d:/poi-excel-helen/商品表2.xlsx");
        HSSFWorkbook workbook = new HSSFWorkbook(is);

        // 创建对工作表的引用。
        // 假定那张表有着缺省名"类别1"（getSheetAt(int index)按索引）
        HSSFSheet sheet = workbook.getSheet("类别1");

        // 读取第一行第一列
        HSSFRow row1 = sheet.getRow(0);
        HSSFCell cell1 = row1.getCell(0);

        // 输出单元内容
        System.out.println("第一行第一列是： " + cell1.getStringCellValue());
    }


    @Test
    public void testCellType() throws Exception {

        InputStream is = new FileInputStream("H:/poi-excel/会员消费商品明细表.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);


        // 读取第一行所有内容
        HSSFRow rowTitle = sheet.getRow(0);
        if (rowTitle != null) {// 行不为空

            // 读取cell
            for (int cellNum = 0; cellNum <= rowTitle.getLastCellNum(); cellNum++) {

                HSSFCell cell = rowTitle.getCell(cellNum);
                if (cell != null) {

                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print("第1行：");
                    System.out.println("第" + (cellNum + 1) + "列：cellType：" + cellType + "，cellValue：" + cellValue);
                }
            }
        }

        // 读取商品列表
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {

            HSSFRow rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空

                // 读取cell
                for (int cellNum = 0; cellNum <= rowData.getLastCellNum(); cellNum++) {

                    HSSFCell cell = rowData.getCell(cellNum);
                    if (cell != null) {

                        int cellType = cell.getCellType();

                        //判断单元格数据类型
                        String cellValue = "";
                        double doubleCellValue = 0;
                        switch (cellType) {
                            case HSSFCell.CELL_TYPE_STRING://字符串
                                System.out.print("【STRING】");
                                cellValue = cell.getStringCellValue();
                                break;

                            case HSSFCell.CELL_TYPE_BOOLEAN://布尔
                                System.out.print("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;

                            case HSSFCell.CELL_TYPE_BLANK://空
                                //注意空和没有值不一样，从来没有录入过内容的单元格不属于任何数据类型，不会走这个case
                                System.out.print("【BLANK】");
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                System.out.print("【NUMERIC】");
                                //cellValue = String.valueOf(cell.getNumericCellValue());

                                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                                    System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    cellValue = sdf.format(date).trim();
                                } else {
                                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                                    System.out.print("【转换成字符串】");
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;

                            case Cell.CELL_TYPE_ERROR:
                                System.out.print("【数据类型错误】");
                                break;

                            default:
                                cellValue = "";
                                break;
                        }

                        System.out.print("第" + (rowNum + 1) + "行：");
                        System.out.print("第" + (cellNum + 1) + "列：cellType：" + cellType + "，cellValue：");
                        if (!"".equals(cellValue)) {
                            System.out.println(cellValue);
                        } else {
                            System.out.println(doubleCellValue);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testFormula() throws Exception{

        InputStream is = new FileInputStream("d:/poi-excel-helen/计算公式.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);

        // 读取第五行第一列
        HSSFRow row = sheet.getRow(4);
        HSSFCell cell = row.getCell(0);

        //公式计算器
        HSSFFormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);

        // 输出单元内容
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA://2
                System.out.print("【公式】");

                //得到公式
                String formula = cell.getCellFormula();
                System.out.print(formula);

                CellValue evaluate = formulaEvaluator.evaluate(cell);
                int evaluateCellType = evaluate.getCellType();
                switch (evaluateCellType) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print("【STRING】");
                        System.out.println(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print("【NUMERIC】");
                        System.out.println(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print("【BOOLEAN】");
                        System.out.println(cell.getBooleanCellValue());
                        break;
                }
                break;
        }
    }

    @Test
    public void testExcel07Read() throws Exception{

        InputStream is = new FileInputStream("H:/poi-excel/商品表2.xlsx");

        //HSSFWorkbook workbook = new HSSFWorkbook(is);
        // 创建Excel2007文件对象
        XSSFWorkbook workbook2007 = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook2007.getSheetAt(0);

        // 读取第一行第一列
        XSSFRow row1 = sheet.getRow(0);
        XSSFCell cell1 = row1.getCell(0);

        // 输出单元内容
        System.out.println("第一行第一列是： " + cell1.getStringCellValue());
    }

    @Test
    public void testExcel03WriteBigData() throws Exception {
        //记录开始时间
        long begin = System.currentTimeMillis();

        //创建一个SXSSFWorkbook
        //-1：关闭 auto-flushing，将所有数据存在内存中
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个sheet
        HSSFSheet sheet = workbook.createSheet();

        //xls文件最大支持65536行
        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream("H:/poi-excel/test-write-0.xlsx");
        workbook.write(out);

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }

    @Test
    public void testExcel07WriteBigData1() throws Exception {

        //记录开始时间
        long begin = System.currentTimeMillis();

        //创建一个SXSSFWorkbook
        //-1：关闭 auto-flushing，将所有数据存在内存中
        SXSSFWorkbook workbook = new SXSSFWorkbook(-1);

        //创建一个sheet
        Sheet sheet = workbook.createSheet();

        for (int rowNum = 0; rowNum < 200000; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
            // 手动向磁盘写数据
            if (rowNum % 10000 == 0) {//一万行向磁盘写一次
                ((SXSSFSheet) sheet).flushRows(0); // 全部写入磁盘
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream("H:/poi-excel/test-write-1.xlsx");
        workbook.write(out);//将临时文件合并，写入最终文件
        out.close();
        // 处理磁盘中处理写入的临时文件夹
        workbook.dispose();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }
    @Test
    public void testExcel07WriteBigData2() throws IOException {

        //记录开始时间
        long begin = System.currentTimeMillis();

    /* 10000：
        * When a new node is created via createRow() and the total number
        * of unflushed records would exceed the specified value, then the
        * row with the lowest index value is flushed and cannot be accessed
        * via getRow() anymore.*/
        SXSSFWorkbook workbook = new SXSSFWorkbook(10000);

        //创建一个sheet
        Sheet sheet = workbook.createSheet();

        for (int rowNum = 0; rowNum < 200000; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }

        System.out.println("done");
        FileOutputStream out = new FileOutputStream("H:/poi-excel/test-write-2.xlsx");
        workbook.write(out);
        out.close();
        // 处理磁盘中处理写入的临时文件夹
        workbook.dispose();

        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }


  /*  @Test
    public void testUtil(HttpServletResponse response) throws IOException {

        String officeName = "officeName";
        String   filename1 = officeName+".xls";
        String filename= new String(filename1.getBytes(),"iso-8859-1");//中文文件名必须使用此句话
        response.setContentType("application/DOWLOAD");
        response.setContentType("application/OCTET-STREAM;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+filename );
        String[] headers = { "id","任务", "单位","子节点","总部门" };  //表格的标题栏
        List<PotentialTask> list =(List<PotentialTask>) service.getTaskData1(request);
        //WorkloadExport 为将要导出的类，也就是表格的一行记录，里面的所有字段都不能为空，必须生成set get方法
        //导出列顺序和类中成员顺序一致
        try {
            ExportExcel<PotentialTask> ex = new ExportExcel<PotentialTask>();  //构造导出类

            OutputStream out = new BufferedOutputStream(response.getOutputStream());

            String  title = list.get(0).getOffice();  //title需要自己指定 比如写Sheet

            ex.exportExcel(title,headers, list, out);  //title是excel表中底部显示的表格名，如Sheet
            out.close();
            //JOptionPane.showMessageDialog(null, "导出成功!");
            //System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("getdownloadTaskData=====");
        return true;
    }*/
}
