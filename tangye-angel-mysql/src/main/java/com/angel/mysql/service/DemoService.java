package com.angel.mysql.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface DemoService {

    List<Map<String,Object>> selectDemoList(String code);

    IPage<Map<String,Object>> selectDemoPageList(IPage<Map<String,Object>> page, JSONObject condition);
}
