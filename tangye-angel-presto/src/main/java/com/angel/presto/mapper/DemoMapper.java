package com.angel.presto.mapper;

import java.util.List;
import java.util.Map;

public interface DemoMapper {

    List<Map<String,Object>> selectDemoList();

    List<Map<String,Object>> selectDemoListHete();
}
