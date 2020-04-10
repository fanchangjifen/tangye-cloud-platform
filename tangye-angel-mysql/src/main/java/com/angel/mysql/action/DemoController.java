package com.angel.mysql.action;

import com.alibaba.fastjson.JSONObject;
import com.angel.mysql.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/select/list")
    public JSONObject selectList(@RequestParam("code") String code){
        JSONObject json = new JSONObject();
        try{
            List<Map<String,Object>> list =  demoService.selectDemoList(code);
            json.put("data",list);
            return json;
        }catch (Exception e){
            json.put("message",e.getMessage());
            return json;
        }
    }
}
