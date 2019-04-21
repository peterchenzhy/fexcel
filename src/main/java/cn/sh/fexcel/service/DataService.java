package cn.sh.fexcel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DataService
 *
 * @author PeterChen
 * @summary DataService
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description DataService
 * @since 2019-04-06 19:23
 */
@Service
public class DataService {

    @Autowired
    private DBTableService dbTableService;

    public List<Map<String, Object>> queryData(String fileName) {
        List<Map<String, Object>> mapList = dbTableService.querySearchTable(fileName);
        return mapList;
    }
}
