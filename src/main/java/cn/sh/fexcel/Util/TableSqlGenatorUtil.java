package cn.sh.fexcel.Util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.sh.fexcel.Util.commons.*;

/**
 * TableSqlGenatorUtil
 * 建表语句生成工具了类
 *
 * @author PeterChen
 * @summary TableSqlGenatorUtil
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description TableSqlGenatorUtil
 * @since 2019-04-04 10:11
 */
@Slf4j
public class TableSqlGenatorUtil {

    public static String createTable(String tableName, List<String> headers) {
        if (headers == null || StringUtils.isEmpty(tableName) || headers.size() == 0) {
            log.warn("传入的数据为空，无法生成table");
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("create table").append(TAB).append(tableName);
        stringBuffer.append("(");
        stringBuffer.append("id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id'").append(COMMA);
        for (int i = 0; i < headers.size(); i++) {
            stringBuffer.append(headers.get(i).trim()).append(TAB).append("varchar(100) NOT NULL DEFAULT ''");
            stringBuffer.append(COMMA);
        }
        stringBuffer.append("PRIMARY KEY (id)");
        stringBuffer.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8");
        log.info(stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static String dropTable(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            log.warn("传入的数据为空，无法生成table");
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("drop table").append(TAB).append(tableName);
        log.info(stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 生成插入模板 跳过第一行，因为是id 自增
     *
     * @param tableName
     * @param headers
     * @author PeterChen
     * @modifier PeterChen
     * @version v1
     * @since 2019/4/6 13:48
     */
    public static String insertTemplate(String tableName, List<String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into").append(TAB).append(tableName);
        sb.append("(");
        for (int i = 0; i < headers.size(); i++) {
            sb.append(headers.get(i).trim());
            if (i != headers.size() - 1) {
                sb.append(COMMA);
            }
        }
        sb.append(")");
        sb.append("values(");
        for (int i = 0; i < headers.size(); i++) {
            sb.append("'").append(PARAM).append("'");
            if (i != headers.size() - 1) {
                sb.append(COMMA);
            }
        }
        sb.append(")");
        log.info(sb.toString());
        return sb.toString();
    }

    public static List<String> insertSQL(String tableName, List<ArrayList<String>> data, List<String> headers) {
        List<String> list = new ArrayList<>();
        String template = insertTemplate(tableName, headers);
        for (List<String> e : data) {
            String str = JSON.toJSONString(e);
            list.add(String.format(template, e.toArray()));
        }
        return list;
    }

    public static String insertExcelTable(String tableName, String excelName) {
        String sql = String.format(INSERT_EXCEL_TABLE_TEMPLATE, tableName, excelName, StatusEnum.有效.getCode());
        log.info(sql);
        return sql;
    }


    //    public final static String INSERT_EXCEL_TABLE_COLLUM_TEMPLATE = "insert into excel_table_collum (table_id,collum_name,status) " +
//            "select et.id,'%s','%s' from excel_table et where et.table_name = '%s'";
    public static List<String> insertExcelTableCollum(String tableName, List<ArrayList<String>> excelHeader, List<String> tableHeader) {
        if (excelHeader == null && excelHeader.isEmpty()) {
            return Lists.newArrayList();
        }
        List<String> collums = excelHeader.get(0);
        List<String> list = new ArrayList<>(collums.size());
        for (int i = 0; i < collums.size(); i++) {
            String sql = String.format(INSERT_EXCEL_TABLE_COLLUM_TEMPLATE, collums.get(i), tableHeader.get(i), StatusEnum.有效.getCode(), tableName);
            log.info(sql);
            list.add(sql);
        }

        return list;
    }

    public static String invalidTableExcelTable(String tableName) {
        return String.format(INVALID_EXCEL_TABLE_TEMPLATE, tableName);
    }

    public static String queryTableExcel(String tableName) {
        return String.format(QUERY_EXCEL_TABLE_TEMPLATE, tableName);
    }

    public static String queryTableExcelCollum(String tableName) {
        return String.format(QUERY_EXCEL_TABLE_COLLUM_TEMPLATE, tableName);
    }

    public static String querySearchTable(String tableName) {
        return String.format(QUERY_SEARCH_TABLE_TEMPLATE, tableName);
    }

    public static String  getDataCount(String tableName) {
        return String.format(GET_DATA_COUNT_TEMPLATE, tableName);
    }
}
