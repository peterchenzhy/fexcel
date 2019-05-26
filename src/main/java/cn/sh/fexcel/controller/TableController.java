package cn.sh.fexcel.controller;

import cn.sh.fexcel.model.*;
import cn.sh.fexcel.service.DBTableService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@CrossOrigin(allowCredentials = "true", origins = "*", maxAge = 3600)
public class TableController {

    @Autowired
    private DBTableService dbTableService;

    /**
     * 获取导入文件列表
     *
     * @param
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/30 17:07
     */
    @RequestMapping(value = "/getFileList", method = RequestMethod.GET)
    public BaseResponse getFileList() {
        List<ExcelTablePo> result = dbTableService.getFileList();
        return BaseResponse.ResponseFacory.success(result);
    }

    /**
     * 获取表头
     *
     * @param tableName
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/30 17:07
     */
    @RequestMapping(value = "/getTableCollum", method = RequestMethod.GET)
    public BaseResponse getTableCollum(@RequestParam("tableName") String tableName) {
        return BaseResponse.ResponseFacory.success(dbTableService.getExcelTableHeaders(tableName));
    }

    /**
     * 获取表头分页
     *
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/30 17:07
     */
    @RequestMapping(value = "/getTableCollumPage", method = RequestMethod.POST)
    public BaseResponse getTableCollumPage(@RequestBody DataQueryPo po) {
        return BaseResponse.ResponseFacory.success(dbTableService.getExcelTableHeadersPage(po));
    }

    /**
     * 编辑表头属性
     *
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/5/3 13:53
     */
    @RequestMapping(value = "/tableCollumManager", method = RequestMethod.POST)
    public BaseResponse tableManager(@RequestBody CollumManager collumManager) {
        dbTableService.updateTableCollumProperty(collumManager);
        return BaseResponse.ResponseFacory.success(new ArrayList<>());
    }


    /**
     * 删除表头
     *
     * @param tableName
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/5/3 13:53
     */
    @RequestMapping(value = "/tableDelete", method = RequestMethod.GET)
    public BaseResponse tableDelete(@RequestParam("tableName") String tableName) {
        dbTableService.tableDelete(tableName);
        return BaseResponse.ResponseFacory.success(new ArrayList<>());
    }


    public static void main(String... args) {

        CollumManager tm = new CollumManager();
        tm.setTableName("myTable");
        Integer[] a = {1, 1, 1};
        CollumProperty cp = new CollumProperty();
        cp.setId(1l);
        cp.setValue(a);

        Integer[] b = {2, 2, 2};
        CollumProperty cp1 = new CollumProperty();
        cp1.setId(3l);
        cp1.setValue(b);

        Integer[] c = {3, 2, 1};
        CollumProperty cp2 = new CollumProperty();
        cp2.setId(3l);
        cp2.setValue(c);

        List list = new ArrayList();
        list.add(cp);
        list.add(cp1);
        list.add(cp2);
        tm.setList(list);


        String json = JSON.toJSONString(tm);
        System.out.println(json);


        CollumManager tm1 = JSON.parseObject(json, CollumManager.class);
        System.out.println(tm1);

    }
}
