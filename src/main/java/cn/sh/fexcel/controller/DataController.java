package cn.sh.fexcel.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * DataController
 *
 * @author PeterChen
 * @summary DataController
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description DataController
 * @since 2019-04-06 19:22
 */
@RestController
public class DataController {

    @RequestMapping(value = "/batchCommit",method = RequestMethod.POST)
    public boolean batchCommit(@RequestParam("excelName") String excelName, @RequestBody Map<String ,String > map){
        System.out.println(JSON.toJSONString(map));
        System.out.println(excelName);
        return true;
    }

    @RequestMapping(value = "/queryTable",method = RequestMethod.GET)
    public List queryTable(@RequestParam("tableName") String excelName){
        System.out.println(JSON.toJSONString(map));
        System.out.println(excelName);
        return true;
    }


}
