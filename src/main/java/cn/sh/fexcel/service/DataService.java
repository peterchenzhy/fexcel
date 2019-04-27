package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.TableSqlGenatorUtil;
import cn.sh.fexcel.Util.commons;
import cn.sh.fexcel.model.DataQueryPo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
@Slf4j
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
                String condition = String.format("%s like '%%%s%%'", key, conditions.get(key));
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
        try {
            List<String> queryStringList = new ArrayList<>(data.size());
            for (Map<String, String> e : data) {
                jdbcTemplate.update(TableSqlGenatorUtil.updateData(tableName, e));
            }
            return true;
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return false;
        }
    }

    public PageInfo queryData(DataQueryPo dataQueryPo) {
        try {
            if (dataQueryPo == null || StringUtils.isEmpty(dataQueryPo.getTableName())) {
                return null;
            }

            if (dataQueryPo.getPageNo() <= 0) {
                dataQueryPo.setPageNo(commons.PAGE_NO);
            }

            if (dataQueryPo.getPageSize() <= 0) {
                dataQueryPo.setPageSize(commons.PAGE_SIZE);
            }
            String clause = buildWhereClause(dataQueryPo.getCondition());
            Map<String, Object> countMap = jdbcTemplate.queryForMap(TableSqlGenatorUtil.getDataCount(dataQueryPo.getTableName(), clause));
            List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.querySearchTable(dataQueryPo.getTableName(),
                    clause, dataQueryPo.getPageNo(), dataQueryPo.getPageSize()));

            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(dataQueryPo.getPageSize());
            pageInfo.setPageNum(dataQueryPo.getPageNo());
            pageInfo.setTotal((long) countMap.get("count"));
            pageInfo.setPages((int) (pageInfo.getTotal() % dataQueryPo.getPageSize() == 0 ?
                    pageInfo.getTotal() / dataQueryPo.getPageSize()
                    : pageInfo.getTotal() / dataQueryPo.getPageSize() + 1));
            pageInfo.setSize(poMap.size());
            pageInfo.setList(poMap);
            return pageInfo;
        } catch (Exception ex) {
            log.error(ex.getCause().getMessage());
        }
        log.info("没有查询导数据，查询参数: {}", JSON.toJSONString(dataQueryPo));
        return new PageInfo(Lists.newArrayList());
    }
}
