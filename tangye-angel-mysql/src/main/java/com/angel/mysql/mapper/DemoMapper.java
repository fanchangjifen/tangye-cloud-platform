package com.angel.mysql.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DemoMapper {

    List<Map<String,Object>> selectDemoList(@Param("code") String code);

    List<Map<String,Object>> selectDemoPageList(IPage<Map<String,Object>> page, @Param("condition")JSONObject condition);
}
