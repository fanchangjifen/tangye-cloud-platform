package com.angel.mysql.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.angel.mysql.mapper.DemoMapper;
import com.angel.mysql.service.DemoService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @DS("slave")
    @Override
    public List<Map<String,Object>> selectDemoList(String code){
        return demoMapper.selectDemoList(code);
    }

    @DS("slave")
    @Override
    public IPage<Map<String,Object>> selectDemoPageList(IPage<Map<String,Object>> page, JSONObject condition){
        page.setRecords(this.demoMapper.selectDemoPageList(page,condition));
        return page;
    }
}
