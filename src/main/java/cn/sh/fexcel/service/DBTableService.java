package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.TableSqlGenatorUtil;
import cn.sh.fexcel.Util.commons;
import cn.sh.fexcel.model.CollumManager;
import cn.sh.fexcel.model.DataQueryPo;
import cn.sh.fexcel.model.ExcelTableCollumPo;
import cn.sh.fexcel.model.ExcelTablePo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DBTableService 数据库db控制类
 *
 * @author PeterChen
 * @summary DBTableService
 * @Copyright (c) PeterChen
 * @Description DBTableService
 * @since 2019-04-06 11:55
 */
@Service
@Slf4j
public class DBTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 建表
     *
     * @param tableName   表名
     * @param tableHeader 数据库表头
     * @param excelHeader excel表头
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/20 10:05
     */
    public boolean createTable(String tableName, List<String> tableHeader, List<ArrayList<String>> excelHeader, String excelName) {

        if (tableHeader == null || StringUtils.isEmpty(tableName) || tableHeader.size() == 0) {
            log.warn("传入的数据为空，无法生成table");
            return false;
        }
        //删表
        ((DBTableService) AopContext.currentProxy()).tableDelete(tableName);
        try {
            //建表
            jdbcTemplate.execute(TableSqlGenatorUtil.createTable(tableName, tableHeader));
            jdbcTemplate.execute(TableSqlGenatorUtil.insertExcelTable(tableName, excelName));
            jdbcTemplate.batchUpdate(TableSqlGenatorUtil.insertExcelTableCollum(tableName, excelHeader, tableHeader).toArray(new String[]{}));
            log.info("建表{}成功", tableName);
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return false;
        }
        return true;
    }

    /**
     * 数据入库
     *
     * @param fileName
     * @param data
     * @param headers
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 13:39
     */
    public boolean batchInsertData(String fileName, List<ArrayList<String>> data, List<String> headers) {
        try {
            List<String> sqls = TableSqlGenatorUtil.insertSQL(fileName, data, headers);
            List<List<String>> partList = Lists.partition(sqls, commons.BATCH_500);
            partList.stream().forEach(list -> {
                jdbcTemplate.batchUpdate(list.toArray(new String[]{}));
            });
            return true;
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return false;
        }
    }

    /**
     * 判断表是否存在 查询excel_table
     *
     * @param tableName
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 15:36
     */
    public boolean checkTableExist(String tableName) {
        List<ExcelTablePo> list = this.getExcelTable(tableName);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public List<ExcelTablePo> getExcelTable(String tableName) {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.queryTableExcel(tableName));
        if (poMap != null && poMap.size() > 0) {
            List<ExcelTablePo> poList = new ArrayList<>(poMap.size());
            poMap.stream().forEach(map -> {
                poList.add(ExcelTablePo.map2po(map));
            });
            return poList;
        }
        return null;
    }


    public List<ExcelTableCollumPo> getExcelTableHeaders(String tableName) {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.queryTableExcelCollum(tableName));
        if (poMap != null && poMap.size() > 0) {
            List<ExcelTableCollumPo> poList = new ArrayList<>(poMap.size());
            poMap.stream().forEach(map -> {
                poList.add(ExcelTableCollumPo.map2po(map));
            });
            return poList;
        }
        return null;
    }

    /**
     * 已导入的文件列表
     *
     * @param
     * @return
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/21 14:17
     */
    public List<ExcelTablePo> getFileList() {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(commons.QUERY_EXCEL_FILELIST_TEMPLATE);
        if (poMap != null && poMap.size() > 0) {
            List<ExcelTablePo> poList = new ArrayList<>(poMap.size());
            poMap.stream().forEach(map -> {
                poList.add(ExcelTablePo.map2po(map));
            });
            return poList;
        }
        return null;
    }


    public List<Map<String, Object>> queryExportData(String tableName) {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.queryExportData(tableName));
        return poMap;
    }

    public Integer getDataCount(String tableName) {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.getDataCount(tableName));
        return Integer.valueOf(String.valueOf(poMap.get(0).get("count")));
    }

    public PageInfo getExcelTableHeadersPage(DataQueryPo dataQueryPo) {
        try {
            if (dataQueryPo == null || StringUtils.isEmpty(dataQueryPo.getTableName())) {
                return new PageInfo(Lists.newArrayList());
            }

            if (dataQueryPo.getPageNo() <= 0) {
                dataQueryPo.setPageNo(commons.PAGE_NO);
            }

            if (dataQueryPo.getPageSize() <= 0) {
                dataQueryPo.setPageSize(commons.PAGE_SIZE);
            }
            String clause = TableSqlGenatorUtil.buildWhereClause(dataQueryPo.getCondition());
            Map<String, Object> countMap = jdbcTemplate.queryForMap(TableSqlGenatorUtil.getDataHeaderCount(dataQueryPo.getTableName(), clause));
            List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.querySearchTableHeader(dataQueryPo.getTableName(),
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
        log.info("没有查询导数据，查询表头参数: {}", JSON.toJSONString(dataQueryPo));
        return new PageInfo(Lists.newArrayList());
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean tableDelete(String tableName) {

        if (StringUtils.isEmpty(tableName)) {
            return true;
        }
        try {
            //删记录
            jdbcTemplate.execute(TableSqlGenatorUtil.deleteTableCollum(tableName));
            jdbcTemplate.execute(TableSqlGenatorUtil.invalidTableExcelTable(tableName));
            //删表
            jdbcTemplate.execute(TableSqlGenatorUtil.dropTable(tableName));
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }
        return true;
    }

    /**
     * 设置表头属性
     *
     * @param collumManager
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/5/3 14:38
     */
    public void updateTableCollumProperty(CollumManager collumManager) {
        List<String> sqls = TableSqlGenatorUtil.updateTableCollumProperty(collumManager);
        List<List<String>> partList = Lists.partition(sqls, commons.BATCH_500);
        partList.stream().forEach(list -> {
            jdbcTemplate.batchUpdate(list.toArray(new String[]{}));
        });
    }
}
