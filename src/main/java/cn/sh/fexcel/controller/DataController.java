package cn.sh.fexcel.controller;

import cn.sh.fexcel.model.BaseResponse;
import cn.sh.fexcel.model.DataQueryPo;
import cn.sh.fexcel.service.DataService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataController
 *
 * @author PeterChen
 * @summary DataController
 * @Copyright (c) PeterChen
 * @Description DataController
 * @since 2019-04-06 19:22
 */
@RestController
@CrossOrigin(allowCredentials = "true", origins = "*", maxAge = 3600)
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/batchCommit", method = RequestMethod.POST)
    public BaseResponse batchCommit(@RequestParam("tableName") String tableName, @RequestBody List<Map<String, String>> data) {
        if (dataService.batchCommit(tableName, data)) {
            return BaseResponse.ResponseFacory.success(null);
        } else {
            return BaseResponse.ResponseFacory.fail(null);
        }
    }

    @RequestMapping(value = "/queryData", method = RequestMethod.POST)
    public BaseResponse queryData(@RequestBody DataQueryPo query) {
        return BaseResponse.ResponseFacory.success(dataService.queryData(query));
    }

    public static void main(String... aa) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "id1");
        map.put("collum1", "A1");
        map.put("collum2", "A2");
        map.put("collum3", "A3");

        System.out.println(JSON.toJSONString(map));
        List list = new ArrayList();
        list.add(map);

        Map<String, String> map1 = new HashMap<>();
        map1.put("id", "id2");
        map1.put("collum1", "B1");
        map1.put("collum2", "B2");
        map1.put("collum3", "B3");
        list.add(map1);

        String result = JSON.toJSONString(list);
        System.out.println(result);

        List<Map<String, String>> list1 = (List) JSON.parse(result);

        System.out.println(list1);

        list1.stream().forEach(e -> {
            e.forEach((k, v) -> {
                System.out.println(" key: " + k + " value: " + v);
            });
        });



        Map<String, String> mapxx = new HashMap<>();
        mapxx.put("c0","1");
        mapxx.put("c1","1");
        DataQueryPo po = new DataQueryPo();
        po.setTableName("extractedon1203eacsamplefollowupbyskulevelforps1");
        po.setCondition(mapxx);
        po.setPageNo(1);
        po.setPageSize(20);
        String resul1t = JSON.toJSONString(po);
        System.out.println(resul1t);

    }
}
