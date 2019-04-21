package cn.sh.fexcel.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class DataResponsePo implements Serializable {
    private List<Map<String, Object>> data;
    private long totalCount;
}
