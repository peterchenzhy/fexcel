
package cn.sh.fexcel.Util;

import lombok.Getter;

/**
 * commons
 *
 * @author PeterChen
 * @summary commons
 * @Copyright (c) 2019, Lianjia Group All Rights Reserved.
 * @Description commons
 * @since 2019-04-06 13:45
 */
public class commons {

    public final static String TAB = " ";
    public final static String CROSS = "_";
    public final static String COMMA = ",";
    public final static String POINT = "\\.";
    public final static String PARAM = "%s";
    public final static String INSERT_EXCEL_TABLE_TEMPLATE = "insert into excel_table (table_name,status) values('%s','%s')";
    public final static String QUERY_EXCEL_TABLE_TEMPLATE = "select * from  excel_table where table_name ='%s' and status = 1";
    public final static String QUERY_EXCEL_TABLE_COLLUM_TEMPLATE = "select etc.* from excel_table_collum etc ,excel_table et where etc.status=1 " +
            "and et.id = etc.table_id and et.status =1 and et.table_name = '%s' ";
    public final static String INVALID_EXCEL_TABLE_TEMPLATE = "update excel_table set status = '0' where table_name ='%s' and status = 1";
    public final static String INSERT_EXCEL_TABLE_COLLUM_TEMPLATE = "insert into excel_table_collum (table_id,collum_name,status) " +
            "select et.id,'%s','%s' from excel_table et where et.table_name = '%s' and et.status =1 ";

    public final static String QUERY_SEARCH_TABLE_TEMPLATE = "select * from  %s where 1=1 ";

    public static final int BATCH_500 = 500;




    public enum StatusEnum {
        有效(1, "有效"),
        无效(0, "无效"),
        待生效(-1, "待生效");

        StatusEnum(int code, String descrition) {
            this.code = code;
            this.descrition = descrition;
        }

        @Getter
        private int code;

        private String descrition;

        public StatusEnum getEnumByCode(Integer req) {
            if (req != null) {
                for (StatusEnum e : StatusEnum.values())
                    if (req == e.getCode()) {
                        return e;
                    }
            }
            throw new IllegalArgumentException("code 参数非法，找不到对应的枚举,code:" + req);

        }

    }

}