package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.ExcelRead;
import cn.sh.fexcel.Util.ExcelWrite;
import cn.sh.fexcel.model.ExcelTableCollumPo;
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
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
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

    public List<ArrayList<String>> readExcel(MultipartFile file) {
        try {
            return new ExcelRead().readExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getCause().getMessage());
            return null;
        }
    }

    public List<ArrayList<String>> readExcelHeader(MultipartFile file) {

        try {
            return new ExcelRead().readExcelHeader(file);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getCause().getMessage());
            return null;
        }
    }


    public void export(String fileName, HttpServletResponse response) {

        List<Map<String, Object>> mapList = dataService.queryData(fileName);
        if (mapList != null && !mapList.isEmpty()) {
            List<ExcelTableCollumPo> headers = dbTableService.getExcelTableHeaders(fileName);
            Workbook workbook = ExcelWrite.write(fileName, headers, mapList);
            try {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName="
                        + new String((fileName + ".xlsx").getBytes("GB2312"), "ISO-8859-1"));
                response.flushBuffer();
                workbook.write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("数据表{}没有数据。", fileName);
        }

    }
}