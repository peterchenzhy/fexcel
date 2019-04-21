package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.TableSqlGenatorUtil;
import cn.sh.fexcel.Util.commons;
import cn.sh.fexcel.model.DataQueryPo;
import cn.sh.fexcel.model.DataResponsePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private String buildWhereClause(Map<String, String> conditions) {
        StringBuilder sb = new StringBuilder();
        if (conditions != null && conditions.size() > 0) {
            sb.append("where ");
            for (String key : conditions.keySet()) {
                String condition = String.format("%s='%s'", key, conditions.get(key));
                sb.append(condition).append(" and ");
            }
        }
        String clause = sb.toString();
        if (clause.endsWith(" and ")) {
            clause = clause.substring(0, clause.length() - 5);
        }
        return clause;
    }

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

    public DataResponsePo queryData(DataQueryPo query) {
        DataResponsePo result = new DataResponsePo();
        try {
            if (query == null || StringUtils.isEmpty(query.getTableName())) {
                return result;
            }

            if (query.getPageNo() <= 0) {
                query.setPageNo(commons.PAGE_NO);
        }

            if (query.getPageSize() <= 0) {
                query.setPageSize(commons.PAGE_SIZE);
    }
            String clause = buildWhereClause(query.getCondition());
            Map<String, Object> countMap = jdbcTemplate.queryForMap(TableSqlGenatorUtil.getDataCount(query.getTableName(), clause));
            List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.querySearchTable(query.getTableName(), clause, query.getPageNo(), query.getPageSize()));

            result.setTotalCount((long) countMap.get("count"));
            result.setData(poMap);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return result;
    }
}
