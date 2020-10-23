package com.angel.elasticsearch7.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.angel.common.enums.*;
import com.angel.elasticsearch7.service.QueryService;
import com.angel.elasticsearch7.util.IndexConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class QueryServiceImpl implements QueryService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchTemplateRequest initSearchTemplateRequest(String... indices){
        SearchTemplateRequest templateRequest = new SearchTemplateRequest();
        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source().timeout(TimeValue.timeValueSeconds(10));
        templateRequest.setRequest(searchRequest);
        templateRequest.setScriptType(ScriptType.INLINE);
        return templateRequest;
    }

    @Override
    public JSONObject initAngleSearchScript(JSONObject json){
        JSONObject script = new JSONObject();
        //分页
        int size = json.getIntValue("size");
        int page = json.getIntValue("page")!=0?json.getIntValue("page"):1;
        script.put(BodyParamEnum.Parameter.SIZE.toString(),size);
        script.put(BodyParamEnum.Parameter.FROM.toString(),(page-1)*size);
        JSONObject bool = new JSONObject();
        JSONArray must = new JSONArray();
        //排序
        JSONArray sort = new JSONArray();
        //根据关键词查询
        JSONArray extendKeywords = json.getJSONArray("extendKeywords");
        if(null != extendKeywords && extendKeywords.size() > 0){
            JSONObject extendKeywordsNested = new JSONObject();
            extendKeywordsNested.put(BodyParamEnum.Body.NESTED.toString(),initMustNestedScript("extendKeywords","extendKeywords.keyword",extendKeywords));
            must.add(extendKeywordsNested);
        }
        //查询匹配查询条件条件
        JSONArray filter = IndexConstant.initQueryFilterArray(json);
        JSONArray mustNot = new JSONArray();
        JSONArray should = new JSONArray();
        //高亮
        String keyword = json.getString("keyword");
        if(StringUtils.isNotBlank(keyword)){
            must.add(IndexConstant.initJsonObject(QueryEnum.simpleFieldsQuery.QUERY_STRING.toString(),
                    IndexConstant.initQueryString(keyword,"title","extendSummary","extendContent")));
            script.put(BodyParamEnum.Body.HIGHLIGHT.toString(),initHighlightScript("basicTitle","extendSummary"));
        }
        //排序字段
        String sortField = json.getString("sortField");
        //最新列表查询标识
        String regionCode = json.getString("regionCode");
        if(StringUtils.isNotBlank(regionCode)){
            //最新：查询本级区划最近7天的
            JSONArray upToDateTabFilter = new JSONArray();
            upToDateTabFilter.add(IndexConstant.initFilterTermObject("regionCode",regionCode));
            upToDateTabFilter.add(IndexConstant.initJsonObject(QueryEnum.otherQuery.RANGE.toString(),
                    IndexConstant.initJsonObject("basicReleaseDate",
                            IndexConstant.initJsonObject(RangeEnum.FROM.toString(),LocalDateTime.of(LocalDate.now(),LocalTime.MIN).minusDays(6)
                                    .toInstant(ZoneOffset.of("+8")).toEpochMilli()))));
            should.add(IndexConstant.initJsonObject(BodyParamEnum.Body.BOOL.toString(),
                    IndexConstant.initJsonObject(BodyParamEnum.Body.FILTER.toString(),upToDateTabFilter)));

            should.add(IndexConstant.initShouldTermObject("regionCode",IndexConstant.THE_SATE_COUNCIL_CODE));
            bool.put(BoolQueryEnum.MINIMUM_SHOULD_MATCH.toString(),1);

            if(StringUtils.isNotBlank(keyword)){
                //存在检索关键字的时候按照评分进行排序
                sort.add(initFieldSortOrder(BodyParamEnum.Parameter.SCORE.toString(),BodyParamEnum.Parameter.DESC.toString()));
            }else{
                //默认按照区划排序
                sort.add(initFieldSortOrder("regionCode",BodyParamEnum.Parameter.DESC.toString()));
            }
        }

        if(StringUtils.isNotBlank(sortField)){
            sort.add(initFieldSortOrder(sortField,
                    StringUtils.isNotBlank(json.getString("order"))?json.getString("order"):BodyParamEnum.Parameter.DESC.toString()));
        }else{
            sort.add(initFieldSortOrder("basicReleaseDate",BodyParamEnum.Parameter.DESC.toString()));
        }
        bool.put(BoolQueryEnum.MUST.toString(),must);
        bool.put(BoolQueryEnum.SHOULD.toString(),should);
        bool.put(BoolQueryEnum.MUST_NOT.toString(),mustNot);
        bool.put(BodyParamEnum.Body.FILTER.toString(),filter);
        script.put(BodyParamEnum.Body.QUERY.toString(),
                IndexConstant.initJsonObject(BodyParamEnum.Body.BOOL.toString(),bool));
        script.put(BodyParamEnum.Body.SORT.toString(),sort);
        List<String> sourceList = Arrays.asList("id","title","regionCode","regionName","organCode","organName");
        script.put(BodyParamEnum.Parameter.SOURCE.toString(),sourceList.toArray());
        return script;
    }

    public JSONObject initMustNestedScript(String pathField,String fieldKey,JSONArray keywords){
        JSONArray should = new JSONArray();
        int minimumShouldMatch = 0;
        if(null!=keywords && keywords.size()>0){
            keywords.forEach(keyword -> {
                should.add(IndexConstant.initJsonObject(QueryEnum.simpleQuery.TERM.toString(),
                        IndexConstant.initJsonObject(fieldKey, keyword)));
            });
            minimumShouldMatch ++;
        }
        JSONObject nested = new JSONObject();
        nested.put(BodyParamEnum.Body.PATH.toString(), pathField);
        JSONObject bool = new JSONObject();
        bool.put(BoolQueryEnum.SHOULD.toString(), should);
        bool.put(BoolQueryEnum.MINIMUM_SHOULD_MATCH.toString(),minimumShouldMatch);
        JSONObject query = new JSONObject();
        query.put(BodyParamEnum.Body.BOOL.toString(), bool);
        nested.put(BodyParamEnum.Body.QUERY.toString(),query);
        return nested;
    }

    public JSONObject initHighlightScript(String... fields){
        JSONObject highlight = new JSONObject();
        highlight.put(HighlightEnum.PRE_TAGS.toString(), "<span style=\"color:red\">");
        highlight.put(HighlightEnum.POST_TAGS.toString(), "</span>");
        highlight.put(HighlightEnum.REQUIRE_FIELD_MATCH.toString(), false);
        JSONObject temp = new JSONObject();
        Arrays.stream(fields).forEach(field ->
                temp.put(field, new JSONObject())
        );
        highlight.put(BodyParamEnum.Body.FIELDS.toString(), temp);
        return highlight;
    }

    public JSONObject initFieldSortOrder(String field,String order){
        JSONObject scoreOrder = IndexConstant.initJsonObject(field,
                IndexConstant.initJsonObject(BodyParamEnum.Body.ORDER.toString(),order));
        return scoreOrder;
    }
}
