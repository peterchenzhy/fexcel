package cn.sh.fexcel.service;

import cn.sh.fexcel.Util.TableSqlGenatorUtil;
import cn.sh.fexcel.Util.commons;
import cn.sh.fexcel.model.ExcelTableCollumPo;
import cn.sh.fexcel.model.ExcelTablePo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
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
    public boolean createTable(String tableName, List<String> tableHeader, List<ArrayList<String>> excelHeader,String excelName) {

        if (tableHeader == null || StringUtils.isEmpty(tableName) || tableHeader.size() == 0) {
            log.warn("传入的数据为空，无法生成table");
            return false;
        }
        try {
            //删表
            jdbcTemplate.execute(TableSqlGenatorUtil.dropTable(tableName));
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }
        try {
            //无效表记录
            jdbcTemplate.execute(TableSqlGenatorUtil.invalidTableExcelTable(tableName));
            jdbcTemplate.execute(TableSqlGenatorUtil.createTable(tableName, tableHeader));
            jdbcTemplate.execute(TableSqlGenatorUtil.insertExcelTable(tableName,excelName));
            jdbcTemplate.batchUpdate(TableSqlGenatorUtil.insertExcelTableCollum(tableName, excelHeader,tableHeader).toArray(new String[]{}));
            log.info("建表{}成功", tableName);
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return false;
        }
        return true;
    }

    /**
     * 更新数据
     *
     * @param fileName
     * @param data
     * @param headers
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 13:39
     */
    public void batchInsertData(String fileName, List<ArrayList<String>> data,List<String> headers) {
        List<String> sqls = TableSqlGenatorUtil.insertSQL(fileName, data, headers);
        List<List<String>> partList = Lists.partition(sqls, commons.BATCH_500);
        partList.stream().forEach(list -> {
            jdbcTemplate.batchUpdate(list.toArray(new String[]{}));
        });

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


    public List<Map<String, Object>> querySearchTable(String tableName) {
        List<Map<String, Object>> poMap = jdbcTemplate.queryForList(TableSqlGenatorUtil.querySearchTable(tableName));
        return poMap;
    }


}
