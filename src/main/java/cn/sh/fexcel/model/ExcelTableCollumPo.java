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
public class ExcelTableCollumPo {

    private Long id;

    private Long tableId;
    private String collumName;

    private Date createTime;

    private Integer status;

    public static ExcelTableCollumPo map2po(Map<String, Object> map) {
        ExcelTableCollumPo po = new ExcelTableCollumPo();
        po.setId((Long) map.get("id"));
        po.setTableId((Long) map.get("table_id"));
        po.setCollumName((String) map.get("collum_name"));
        po.setCreateTime((Date) map.get("create_time"));
        po.setStatus((Integer) map.get("status"));
        return po;
    }
}
