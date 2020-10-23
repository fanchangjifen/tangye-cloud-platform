package com.angel.elasticsearch7.action;

import com.alibaba.fastjson.JSONObject;
import com.angel.common.http.R;
import com.angel.elasticsearch7.service.QueryService;
import com.angel.elasticsearch7.util.IndexConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/search/page/list")
    public R getAnglePageList(@RequestBody JSONObject data){
        Integer page = data.getIntValue("page");
        try{
            SearchTemplateRequest templateRequest = this.queryService.initSearchTemplateRequest(IndexConstant.E_ANGLE_INDEX);
            JSONObject script = this.queryService.initAngleSearchScript(data);
            templateRequest.setScript(script.toJSONString());
            templateRequest.setScriptParams(new HashMap<>());
            SearchResponse response = client.searchTemplate(templateRequest, RequestOptions.DEFAULT).getResponse();
            SearchHits searchHits = response.getHits();
            SearchHit[] hits = searchHits.getHits();
            List<Map<String,Object>> list = new ArrayList<>();
            Arrays.stream(hits).forEach(hit -> {
                Map<String,Object> map = hit.getSourceAsMap();
                Map<String, HighlightField> highlight = hit.getHighlightFields();
                HighlightField basicTitleField = highlight.get("basicTitle");
                HighlightField extendSummaryField = highlight.get("extendSummary");
                StringBuilder titleBuilder = new StringBuilder();
                StringBuilder summaryBuilder = new StringBuilder();
                if(null != basicTitleField ){
                    Arrays.stream(basicTitleField.fragments()).forEach(text ->
                            titleBuilder.append(text)
                    );
                }
                if(null != extendSummaryField ){
                    Arrays.stream(extendSummaryField.fragments()).forEach(text ->
                            summaryBuilder.append(text)
                    );
                }
                map.put("hBasicTitle",titleBuilder.toString());
                map.put("hSummaryTitle",summaryBuilder.toString());
                list.add(map);
            });
            //封装返回结果
            R result = new R();
            result.setCount(searchHits.getTotalHits().value);
            result.setPage(page);
            result.setData(list);
            return new R().ok(result);
        }catch (Exception e){
            log.error("/query/search/page/list error :" + e.getMessage());
            return new R().failed();
        }
    }
}
