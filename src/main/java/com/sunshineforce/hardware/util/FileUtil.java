package com.sunshineforce.hardware.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines() throws InterruptedException{
        File file = new File("/Users/jie.liu/Desktop/1.log");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                int start = StringUtils.indexOf(tempString, "ad ba");
                String message = tempString.substring(start);
                Thread.sleep(1000);
                System.out.println("line: "+line+"message: "+message);
                UdpUtil.sendUdpRequest(ByteUtil.HexToByte(StringUtils.replace(message, " ", "")));
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static ArrayList<String> readExcelToList(MultipartFile file){
        ArrayList<String> list = new ArrayList<>();
        Workbook workbook = null;
        if(file == null){
            return null;
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        try{
            if(".xls".equals(suffix.toLowerCase())){
                workbook = new HSSFWorkbook(file.getInputStream());
            }
            else if(".xlsx".equals(suffix.toLowerCase())){
                workbook = new XSSFWorkbook(file.getInputStream());
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int sheetNum = workbook.getNumberOfSheets();
        for(int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++){
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int rowNum = sheet.getLastRowNum();
            for(int rowIndex = 0; rowIndex <= rowNum; rowIndex++){
                Row row = sheet.getRow(rowIndex);
                if(row == null){
                    continue;
                }
                int colNum = row.getPhysicalNumberOfCells();
                for(int colIndex = 0; colIndex < colNum; colIndex++){
                    Cell cell = row.getCell(colIndex);
                    switch(cell.getCellType()){
                        case Cell.CELL_TYPE_NUMERIC:{
                            list.add((int)Math.ceil(Double.valueOf(cell.getNumericCellValue())) + "");
                            break;
                        }
                        case Cell.CELL_TYPE_STRING:{
                            list.add(cell.getStringCellValue());
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void main(String args[]) throws InterruptedException{
        readFileByLines();
    }
}
