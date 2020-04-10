package com.angel.mysql.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DemoMapper {

    List<Map<String,Object>> selectDemoList(@Param("code") String code);
}
