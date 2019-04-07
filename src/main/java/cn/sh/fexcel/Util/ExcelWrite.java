package cn.sh.fexcel.Util;

import cn.sh.fexcel.model.ExcelTableCollumPo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ExcelRead
 *
 * @author PeterChen
 * @summary ExcelRead
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description ExcelRead
 * @since 2019-04-04 10:48
 */
public class ExcelWrite {

    public static Workbook write(String fileName, List<ExcelTableCollumPo> headers, List<Map<String, Object>> data) {
// 设置表头
        List<String> headList = new ArrayList<String>();
        headers.stream().forEach(header -> {
            headList.add(header.getCollumName().trim());
        });

        SXSSFWorkbook  workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet();
        createHead(workbook, sheet, headList);

        // 设置日期格式
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (int i = 0; i < data.size(); i++) {
            SXSSFRow row = sheet.createRow(rowNum);
            for (int j = 0; j < headers.size(); j++) {
                row.createCell(j).setCellValue(data.get(i).get(headers.get(j).getCollumName()).toString());
            }
            rowNum++;
        }
        return workbook;

    }

    // 创建表头
    private static void createHead(SXSSFWorkbook  workbook, SXSSFSheet  sheet, List headList) {

        SXSSFRow row = sheet.createRow(0);
        // 设置为居中加粗
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        SXSSFCell cell;
        for (int i = 0; i < headList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(headList.get(i).toString());
            cell.setCellStyle(style);
        }

    }
}
