package com.angel.presto.service.impl;

import com.angel.presto.mapper.DemoMapper;
import com.angel.presto.service.DemoService;
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

    @Override
    public List<Map<String, Object>> selectDemoList() {
        return demoMapper.selectDemoList();
    }

    @Override
    public List<Map<String, Object>> selectDemoListHete() {
        return demoMapper.selectDemoListHete();
    }
}
