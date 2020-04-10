package com.angel.mysql.service;

import java.util.List;
import java.util.Map;

public interface DemoService {

    List<Map<String,Object>> selectDemoList(String code);
}
