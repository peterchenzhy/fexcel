package cn.sh.fexcel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * ExcelTablePo
 *
 * @author PeterChen
 * @summary ExcelTablePo
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description ExcelTablePo
 * @since 2019-04-06 15:25
 */
@Setter
@Getter
public class ExcelTablePo {

    private Long id;

    private String tableName;

    private Date createTime;

    private Integer status;

    public static ExcelTablePo map2po(Map<String, Object> map) {
        ExcelTablePo po = new ExcelTablePo();
        po.setId((Long) map.get("id"));
        po.setTableName((String) map.get("table_name"));
        po.setCreateTime((Date) map.get("create_time"));
        po.setStatus((Integer) map.get("status"));
        return po;
    }
}
