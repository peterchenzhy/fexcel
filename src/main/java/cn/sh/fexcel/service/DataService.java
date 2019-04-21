package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.TableSqlGenatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DataService
 *
 * @author PeterChen
 * @summary DataService
 * @Copyright (c) PeterChen
 * @Description DataService
 * @since 2019-04-06 19:23
 */
@Service
public class DataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DBTableService dbTableService;

    public List<Map<String, Object>> queryExportData(String tablename) {
        List<Map<String, Object>> mapList = dbTableService.queryExportData(tablename);
        return mapList;
    }

    /**
     * 批量更新
     *
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/21 16:02
     */
    public boolean batchCommit(String tableName, List<Map<String, String>> data) {

        List<String> queryStringList = new ArrayList<>(data.size());
        for (Map<String, String> e : data) {
            jdbcTemplate.update(TableSqlGenatorUtil.updateData(tableName, e));
        }
        return true;
    }

}
