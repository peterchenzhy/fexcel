package cn.sh.fexcel.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * CollumManager
 *
 * @author PeterChen
 * @summary CollumManager
 * @Copyright (c) 2019, PeterChen.
 * @Description CollumManager
 * @since 2019-04-30 17:15
 */
@Data
public class CollumManager {

    private String tableName;
    private List<CollumProperty> list;


}