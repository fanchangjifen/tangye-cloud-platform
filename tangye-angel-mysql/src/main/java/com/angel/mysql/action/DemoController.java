package com.angel.mysql.action;

import com.alibaba.fastjson.JSONObject;
import com.angel.common.http.R;
import com.angel.mysql.service.DemoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public R selectList(@RequestParam("code") String code){
        try{
            List<Map<String,Object>> list =  demoService.selectDemoList(code);
            return R.ok(list);
        }catch (Exception e){
            log.error("mysql/demo/select/list error : " + e.getMessage());
            return R.failed();
        }
    }

    @PostMapping("/select/page/list")
    public R selectPageList(@RequestBody JSONObject json){
        try{
            JSONObject condition = json.getJSONObject("condition");
            IPage<Map<String,Object>> pager = demoService.selectDemoPageList(this.getIPage(json), condition);
            return R.ok(pager.getCurrent(),pager.getTotal(),pager.getRecords());
        }catch (Exception e){
            log.error("mysql/demo/select/page/list error : " + e.getMessage());
            return R.failed();
        }
    }

    protected Page getIPage(JSONObject param) {
        int _size = 10, _offset = 1;
        //偏移位置
        if(StringUtils.isNotBlank(param.getString("offset"))){
            _offset = param.getIntValue("offset");
        }
        //每页大小
        if(StringUtils.isNotBlank(param.getString("limit"))){
            _size = param.getIntValue("limit");
        }
        int pageNum = (param.getIntValue("page")==0)?(_offset/_size + 1):param.getIntValue("page");
        return new Page(pageNum, _size);
    }
}
