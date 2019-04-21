
package cn.sh.fexcel.Util;

import lombok.Getter;

/**
 * commons
 *
 * @author PeterChen
 * @summary commons
 * @Copyright (c) PeterChen
 * @Description commons
 * @since 2019-04-06 13:45
 */
public class commons {

    public final static String TAB = " ";
    public final static String CROSS = "_";
    public final static String COMMA = ",";
    public final static String POINT = "\\.";
    public final static String PARAM = "%s";
    public final static String INSERT_EXCEL_TABLE_TEMPLATE = "insert into excel_table (table_name,excel_name,status) values('%s','%s','%s')";
    public final static String QUERY_EXCEL_TABLE_TEMPLATE = "select * from  excel_table where table_name ='%s' and status = 1 ";
    public final static String QUERY_EXCEL_FILELIST_TEMPLATE = "select * from  excel_table where status = 1 ";

    public final static String QUERY_EXCEL_TABLE_COLLUM_TEMPLATE = "select etc.* from excel_table_collum etc ,excel_table et where etc.status=1 " +
            "and et.id = etc.table_id and et.status =1 and et.table_name = '%s'  order by etc.id";
    public final static String INVALID_EXCEL_TABLE_TEMPLATE = "update excel_table set status = '0',update_time=now() where table_name ='%s' and status = 1";
    public final static String INSERT_EXCEL_TABLE_COLLUM_TEMPLATE = "insert into excel_table_collum (table_id,excel_collum_name,table_collum_name,status) " +
            "select et.id,'%s','%s','%s' from excel_table et where et.table_name = '%s' and et.status =1 ";

    public final static String QUERY_SEARCH_TABLE_TEMPLATE = "select * from  %s order by id ";
    public final static String QUERY_SEARCH_TABLE_WITH_CONDITIONS_TEMPLATE = "select * from  %1$s %2$s order by id limit %3$d, %4$d";
    public final static String QUERY_EXPORT_TABLE_DATA_TEMPLATE = "select * from  %s where 1=1 order by id ";
    public final static String GET_DATA_COUNT_TEMPLATE = "select count(0) as count from %s ";
    public final static String GET_DATA_COUNT_WITH_CONDITIONS_TEMPLATE = "select count(0) as count from %1$s %2$s ";

    public static final int PAGE_NO = 1;
    public static final int PAGE_SIZE = 20;

    public final  static String BATCH_UPDATE_DATA = "update %s set %s where id = %s ";

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
