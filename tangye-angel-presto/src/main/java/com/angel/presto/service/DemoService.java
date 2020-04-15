package com.angel.presto.service;

import java.util.List;
import java.util.Map;

public interface DemoService {

    /**
     * @Description 同类型，不同地址的数据源联合查询(mysql&mysql)
     * @author lizaiyong
     * @date 2020/4/14 18:43
     * @param
     * @return
     **/
    List<Map<String,Object>> selectDemoList();

    /**
     * @Description 异构数据源联合查询（mysql& postgresql)
     * @author lizaiyong
     * @date 2020/4/14 18:43
     * @param
     * @return
     **/
    List<Map<String,Object>> selectDemoListHete();

}
