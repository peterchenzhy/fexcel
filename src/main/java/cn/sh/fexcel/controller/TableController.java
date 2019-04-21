package cn.sh.fexcel.controller;

import cn.sh.fexcel.model.ExcelTableCollumPo;
import cn.sh.fexcel.model.ExcelTablePo;
import cn.sh.fexcel.service.DBTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TableController
 *
 * @author PeterChen
 * @summary TableController
 * @Copyright (c) PeterChen
 * @Description TableController
 * @since 2019-04-21 14:09
 */
@RestController
public class TableController {

    @Autowired
    private DBTableService dbTableService ;

    @RequestMapping(value = "/getFileList",method = RequestMethod.GET)
    public List<ExcelTablePo> getFileList(){
        return dbTableService.getFileList();
    }

    @RequestMapping(value = "/getTableCollum",method = RequestMethod.GET)
    public List<ExcelTableCollumPo> getTableCollum(@RequestParam("tableName") String tableName){
        return dbTableService.getExcelTableHeaders(tableName);
    }
}
