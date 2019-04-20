package cn.sh.fexcel.Util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class ExcelUtil {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 获得path的后缀名
     *
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }

    /**
     * 获得path的后缀名
     * 空格转换为_
     *
     * @param path
     * @return
     */
    public static String fileName(String path, boolean tableFormat) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        String filename;
        if (path.contains(POINT)) {
            filename = path.substring(0, path.lastIndexOf(POINT));
            if (tableFormat) {
                //空格转换为_
                filename = filename.replaceAll(commons.TAB, EMPTY);
                filename = filename.replaceAll(commons.POINT, EMPTY);
                log.info(filename);
            }
            return filename;
        }
        return EMPTY;
    }


    /**
     * 单元格格式
     *
     * @param hssfCell
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    public static String getHValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == CellType.NUMERIC) {
            String cellValue = "";
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * 单元格格式
     *
     * @return
     */
    public static String getXValue(XSSFCell cell) {
        DataFormatter df = new DataFormatter();
        if (cell != null && CellType.NUMERIC.equals(cell.getCellType()) && DateUtil.isCellDateFormatted(cell)) {

            log.warn(cell.getCellStyle().getDataFormatString());
            log.warn(String.valueOf(cell.getCellStyle().getDataFormat()));


            return new DateTime(cell.getDateCellValue().getTime()).toString("yyyy/MM/dd HH:mm:ss");
        }
        String cellValue;
        if (cell.getCellType().equals(CellType.STRING)) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType().equals(CellType.FORMULA)) {
            cellValue = "="+cell.getCellFormula();
        } else {
            cellValue = df.formatCellValue(cell);
            log.info(" cellValue= df.formatCellValue(cell) ==" + cellValue);
        }
        // 单元格函数处理
//        cellValue = df.formatCellValue(cell, new XSSFFormulaEvaluator((XSSFWorkbook) row.getSheet().getWorkbook()));

        //excel中特殊空格字符，会导致无法trim函数无法去除
//        cellValue = cellValue.replaceAll("\\u00A0", "");
        return StringUtils.trimWhitespace(cellValue);
    }

    /**
     * 自定义xssf日期工具类
     *
     * @author lp
     */
    static class XSSFDateUtil extends DateUtil {
        protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
            return DateUtil.absoluteDay(cal, use1904windowing);
        }
    }
}