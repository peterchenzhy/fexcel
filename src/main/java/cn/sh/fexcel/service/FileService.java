package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.ExcelRead;
import cn.sh.fexcel.Util.ExcelWrite;
import cn.sh.fexcel.model.ExcelTableCollumPo;
import cn.sh.fexcel.model.ExcelTablePo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FileService 文件操作service
 *
 * @author PeterChen
 * @summary FileService
 * @Copyright (c) PeterChen
 * @Description FileService
 * @since 2019-04-06 11:10
 */
@Service
@Slf4j
public class FileService {

    @Autowired
    private DataService dataService;
    @Autowired
    private DBTableService dbTableService;

    @Autowired
    private ExcelRead excelRead;


    /**
     * 读文件
     *
     * @param file
     * @param startRow
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/21 11:11
     */
    public List<ArrayList<String>> readExcel(MultipartFile file, Integer startRow) {
        try {
            return excelRead.readExcel(file, startRow);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getCause().getMessage());
            return null;
        }
    }

    /**
     * 读文件头
     *
     * @param file
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/21 11:10
     */
    public List<ArrayList<String>> readExcelHeader(MultipartFile file) {
        return readExcel(file, 0);
    }

    /**
     * 导出
     *
     * @param tableName
     * @param response
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/21 11:10
     */
    public void export(String tableName, HttpServletResponse response) {

        List<ExcelTablePo> table = dbTableService.getExcelTable(tableName) ;
        if (table==null||table.isEmpty()) {
            log.warn("数据表不存在 {}", tableName);
            return;
        }
        List<Map<String, Object>> mapList = dataService.queryExportData(tableName);
        if (mapList != null && !mapList.isEmpty()) {
            List<ExcelTableCollumPo> headers = dbTableService.getExcelTableHeaders(tableName);
            Workbook workbook = ExcelWrite.write(headers, mapList);
            try {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName="
                        + new String((table.get(0).getExcelName() + ".xlsx").getBytes("GB2312"), "ISO-8859-1"));
                response.flushBuffer();
                workbook.write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("数据表{}没有数据。", table.get(0).getExcelName());
        }

    }
}
