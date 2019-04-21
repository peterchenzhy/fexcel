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
 * @Copyright (c) PeterChen
 * @Description ExcelTablePo
 * @since 2019-04-06 15:25
 */
@Setter
@Getter
public class ExcelTableCollumPo {

    private Long id;
    private Long tableId;
    private String tableCollumName;
    private String excelCollumName;
    private Date createTime;
    private Integer status;
    private Date updateTime;
    private Integer canEdit;
    private Integer canView;
    private Integer canSearch;


    public static ExcelTableCollumPo map2po(Map<String, Object> map) {
        ExcelTableCollumPo po = new ExcelTableCollumPo();
        po.setId((Long) map.get("id"));
        po.setTableId((Long) map.get("table_id"));
        po.setTableCollumName((String) map.get("table_collum_name"));
        po.setExcelCollumName((String) map.get("excel_collum_name"));
        po.setCreateTime((Date) map.get("create_time"));
        po.setUpdateTime((Date) map.get("update_time"));
        po.setStatus((Integer) map.get("status"));
        po.setCanEdit((Integer) map.get("can_edit"));
        po.setCanSearch((Integer) map.get("can_search"));
        po.setCanView((Integer) map.get("can_view"));
        return po;
    }
}
