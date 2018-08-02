package com.hang.pulllog.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static Workbook makeNewExcel(String... cellNames){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0");
        sheet.setDefaultColumnWidth(30);
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置这些样式
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        int i=0;
        Row row = sheet.createRow(0);
        for(String cellName:cellNames){
            row.createCell(i).setCellStyle(cellStyle);
            row.createCell(i).setCellValue(cellName);
            i++;
        }

        workbook.setSheetName(0, "信息");
        return workbook;
    }
}
