package cn.sh.fexcel.controller;

import cn.sh.fexcel.Util.ExcelUtil;
import cn.sh.fexcel.service.DBTableService;
import cn.sh.fexcel.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * FileController 文件上传下载
 *
 * @author PeterChen
 * @summary FileController
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description FileController
 * @since 2019-04-04 10:14
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private DBTableService dbTableService;

    /**
     * 导入
     *
     * @param file
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 17:51
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam(value = "excelFile") MultipartFile file) {
        if (file == null) {
            return "上传文件为空";
        }
        //文件名
        String fileName = ExcelUtil.fileName(file.getOriginalFilename().trim());
        //文件头
        List headers = fileService.readExcelHeader(file);
        //查询表是否存在
        if (dbTableService.checkTableExist(fileName)) {

        }

        //建表
        if (dbTableService.createTable(fileName, headers)) {
            //插入数据
            dbTableService.batchInsertData(fileName, fileService.readExcel(file), headers);
        }
        return null;
    }

    /**
     * 导出
     *
     * @param fileName
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 17:51
     */
    @RequestMapping(value = "/file/export", method = RequestMethod.GET)
    public void fileUpload(@RequestParam(value = "fileName")String fileName, HttpServletResponse response) {
          fileService.export(fileName,response);
    }
}
