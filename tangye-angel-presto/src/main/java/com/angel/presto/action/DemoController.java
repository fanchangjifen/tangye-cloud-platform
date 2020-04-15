package com.angel.presto.action;

import com.angel.common.http.R;
import com.angel.presto.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/select/list")
    public R list(){
        try{
            List<Map<String,Object>> list =  demoService.selectDemoList();
            return R.ok(list);
        }catch (Exception e){
            log.error("presto/demo/select/list error : " + e.getMessage());
            return R.failed();
        }
    }

    @GetMapping("/select/listHete")
    public R listHete(){
        try{
            List<Map<String,Object>> list =  demoService.selectDemoListHete();
            return R.ok(list);
        }catch (Exception e){
            log.error("presto/demo/select/list error : " + e.getMessage());
            return R.failed();
        }
    }


}
