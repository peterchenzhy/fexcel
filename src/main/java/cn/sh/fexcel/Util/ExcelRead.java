package cn.sh.fexcel.Util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelRead
 *
 * @author PeterChen
 * @summary ExcelRead
 * @Copyright (c) PeterChen
 * @Description ExcelRead
 * @since 2019-04-04 10:48
 */
@Component
public class ExcelRead {
//    public int totalRows; //sheet中总行数
//    public int totalCells ; //每一行总单元格数

    private static ThreadLocal<Integer> totalCells = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<Integer> totalRows = ThreadLocal.withInitial(() -> 0);

    /**
     * read the Excel .xlsx,.xls
     *
     * @param file     jsp中的上传文件
     * @param startRow
     * @return
     * @throws IOException
     */
    public List<ArrayList<String>> readExcel(MultipartFile file, Integer startRow) throws IOException {
        if (file == null || ExcelUtil.EMPTY.equals(file.getOriginalFilename().trim())) {
            return null;
        } else {
            String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
            if (!ExcelUtil.EMPTY.equals(postfix)) {
                if (ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equalsIgnoreCase(postfix)) {
                    return readXls(file, startRow);
                } else if (ExcelUtil.OFFICE_EXCEL_2010_POSTFIX.equalsIgnoreCase(postfix)) {
                    return readXlsx(file, startRow);
                } else {
                    return null;
                }
            }
        }
        return null;
    }


    /**
     * read the Excel 2010 .xlsx read data
     *
     * @param file
     * @param startRow
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public List<ArrayList<String>> readXlsx(MultipartFile file, Integer startRow) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        if (startRow == null) {
            startRow = 0;
        }
        // IO流读取文件
        InputStream input = null;
        XSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new XSSFWorkbook(input);
            //读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                totalRows.set(xssfSheet.getLastRowNum());
                //读取Row,从第二行开始
                for (int rowNum = startRow; rowNum <= totalRows.get(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        if (totalCells.get() == 0) {
                            totalCells.set((int) xssfRow.getLastCellNum());
                        }
                        rowList = new ArrayList<String>();
                        for (int c = 0; c < totalCells.get(); c++) {
                            XSSFCell cell = xssfRow.getCell(c);
                            if (cell == null) {
                                rowList.add(ExcelUtil.EMPTY);
                                continue;
                            }
                            rowList.add(ExcelUtil.getXValue(cell).trim());
                        }
                        list.add(rowList);
                    }
                    if (startRow == 0) {
                        break;
                    }
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    /**
     * read the Excel 2003-2007 .xls data
     *
     * @param file
     * @param startRow
     * @return
     * @throws IOException
     */
    public List<ArrayList<String>> readXls(MultipartFile file, Integer startRow) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        if (startRow == null) {
            startRow = 0;
        }
        // IO流读取文件
        InputStream input = null;
        HSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new HSSFWorkbook(input);
            //读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                totalRows.set(hssfSheet.getLastRowNum());
                //读取Row,从第二行开始
                for (int rowNum = startRow; rowNum <= totalRows.get(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        rowList = new ArrayList<String>();
                        if (totalCells.get() == 0) {
                            totalCells.set((int) hssfRow.getLastCellNum());
                        }
                        //读取列，从第一列开始
                        for (short c = 0; c < totalCells.get(); c++) {
                            HSSFCell cell = hssfRow.getCell(c);
                            if (cell == null) {
                                rowList.add(ExcelUtil.EMPTY);
                                continue;
                            }
                            rowList.add(ExcelUtil.getHValue(cell).trim());
                        }
                        list.add(rowList);
                    }
                    if (startRow == 0) {
                        break;
                    }
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
