package cn.sh.fexcel.controller;

import cn.sh.fexcel.model.BaseResponse;
import cn.sh.fexcel.model.ExcelTablePo;
import cn.sh.fexcel.service.DBTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*", maxAge = 3600)
public class TableController {

    @Autowired
    private DBTableService dbTableService;

    @RequestMapping(value = "/getFileList", method = RequestMethod.GET)
    public BaseResponse getFileList() {
        List<ExcelTablePo> result = dbTableService.getFileList();
        return BaseResponse.ResponseFacory.success(result);
    }

    @RequestMapping(value = "/getTableCollum", method = RequestMethod.GET)
    public BaseResponse getTableCollum(@RequestParam("tableName") String tableName) {
        return BaseResponse.ResponseFacory.success(dbTableService.getExcelTableHeaders(tableName));
    }
}
