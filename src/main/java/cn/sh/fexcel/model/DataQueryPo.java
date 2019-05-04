package cn.sh.fexcel.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
public class DataQueryPo implements Serializable {
    private String tableName;
    private Map<String, String> condition;
    private int pageNo;
    private int pageSize;
}
