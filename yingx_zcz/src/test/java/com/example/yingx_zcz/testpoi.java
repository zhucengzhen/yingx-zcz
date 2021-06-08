package com.example.yingx_zcz;
import com.baizhi.YingxZczApplication;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = YingxZczApplication.class)
public class testpoi {


    @Test
    public void testUserMapper() {
    //创建eccel文档
        Workbook workbook = new HSSFWorkbook();
        //创建工作表
         Sheet sheet = workbook.createSheet("学生信息表");
         //创建一行，参数：行下标，参数从0开始
         Row row = sheet.createRow(1);
        //创建单元格 参数单元格下标
        Cell cell = row.createCell(3);
        //给单元格设置内容
        cell.setCellValue("这是第二行，第四个单元格");
        try{
            //导出excel
            workbook.write(new FileOutputStream(new File("D://aa.xls")));
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
   public void exportFiles(){
        //创建文档
         HSSFWorkbook workbook = new HSSFWorkbook();
         //创建一个工作表
         HSSFSheet sheet = workbook.createSheet();
         //创建合并单元格对象

         CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 4);
        //S设置合并单元格
        sheet.addMergedRegion(cellAddresses);
        //设置列宽
        sheet.setColumnWidth(3,20*256);
        //创建字体对象
         Font font = workbook.createFont();
         font.setFontName("微软雅黑");//字体
        font.setBold(true);//加粗
        //创建样式对象
        CellStyle cellStyles = workbook.createCellStyle();
        //设置单元格文字居中
        cellStyles.setAlignment(HorizontalAlignment.CENTER);
        //创建行
         Row row = sheet.createRow(0);
         row.setHeight((short) (25*20));
         //设置一个单元格
         Cell cell = row.createCell(0);
         //给单元格设置内容
        cell.setCellValue("用户信息统计表");

        //目录行
        String[] titles={"id","名字","年龄","生日"};
        Row row1 = sheet.createRow(1);
        //遍历目录数组
        for (int i = 0; i < titles.length; i++) {
            //创建一个单元格
             Cell cell1 = row1.createCell(i);
             //将数据放入单元格
            cell1.setCellValue(titles[i]);

        }
       /* //从后台查询所有数据
         ArrayList<Emp> emps= new ArrayList<>();
        emps.add(new Emp("1","小黄",18,new Date()));
        emps.add(new Emp("2","小黑",19,new Date()));
        emps.add(new Emp("3","小可爱",20,new Date()));
        //创建日期格式对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        short format = dataFormat.getFormat("yyyy-MM-dd");
        //创建单元格样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期格式交给样式对象
        cellStyle.setDataFormat(format);
        //遍历数据
        for (int i = 0; i < emps.size(); i++) {

            //创建一行
            Row rows = sheet.createRow(i + 2);

            //创建单元格并填充数据
            rows.createCell(0).setCellValue(emps.get(i).getId());
            rows.createCell(1).setCellValue(emps.get(i).getName());
            rows.createCell(2).setCellValue(emps.get(i).getAge());

            //创建生日单元格
            Cell cell1 = rows.createCell(3);
            //给生日单元格赋值
            cell1.setCellValue(emps.get(i).getBir()); //日志
            //给单元格设置样式
            cell1.setCellStyle(cellStyle);

        }*/

        try {
            //导出单元格
            workbook.write(new FileOutputStream(new File("D://poi测试s.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
