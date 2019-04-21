package cn.sh.fexcel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class DataController {

    @RequestMapping(value = "/batchCommit",method = RequestMethod.POST)
    public boolean batchCommit(@RequestParam("tableName") String tableName, @RequestBody List<Map<String ,String >> data){
        System.out.println(JSON.toJSONString(data));
        System.out.println(tableName);
        return true;
    }


    public static void main(String... aa){
        Map<String,String> map = new HashMap<>();
        map.put("id","id1");
        map.put("collum1","A1");
        map.put("collum2","A2");
        map.put("collum3","A3");

        System.out.println(JSON.toJSONString(map));
        List list = new ArrayList();
        list.add(map);

        Map<String,String> map1 = new HashMap<>();
        map1.put("id","id2");
        map1.put("collum1","B1");
        map1.put("collum2","B2");
        map1.put("collum3","B3");
        list.add(map1);

        String result = JSON.toJSONString(list) ;
        System.out.println(result);

        List<Map<String,String>> list1 = (List) JSON.parse(result);

        System.out.println(list1);

        list1.stream().forEach(e->{
            e.forEach((k,v)->{
                System.out.println(" key: "+k+ " value: "+v );
            });
        });



    }
}
