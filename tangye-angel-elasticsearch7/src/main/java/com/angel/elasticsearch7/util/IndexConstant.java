package com.angel.elasticsearch7.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.angel.common.enums.*;
import com.angel.common.utils.CTools;
import com.angel.common.utils.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;

@Slf4j
public class IndexConstant {

    public static final String E_ANGLE_PIPELINE = "attachment";

    public static final String E_ANGLE_INDEX = "e-angle-prod-index-v1";

    public static final String E_ANGLE_ANALYZER = "my_analyzer";

    public static final String E_ANGLE_LOG_TIME_FORMAT = "yyyy.MM.dd";
    
    public static final String E_ANGLE_LOG_INDEX = "e-angle-log";

    public static final String E_ANGLE_LOG_INDEX_ALIAS = "eanglelog";

    public static final String THE_SATE_COUNCIL_CODE = "000000";

    public static final String NONE_STRING = "";

    public static String UnifiedTransLogIndexName(int days){
        if(days>0){
            return transAngleLogDaysIndices(days);
        }else {
            return E_ANGLE_LOG_INDEX_ALIAS;
        }
    }

    private static String transAngleLogDaysIndices(int days){
        String[] indices = new String[days];
        for(int i=0;i<days;i++){
            String index = E_ANGLE_LOG_INDEX + "-" + CTools.localDateTime2String(LocalDateTime.now().minusDays(i),E_ANGLE_LOG_TIME_FORMAT);
            indices[i] = index;
        }
        return StringUtils.join(indices,",");
    }

    public static XContentBuilder initEAngleImplementation(JSONObject data,String operation){
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                IndexConstant.builderField("id", getStringField(data, "id"),builder);
                IndexConstant.builderField("title", getStringField(data, "policyId"),builder);
                IndexConstant.builderField("regionCode", getStringField(data,"regionCode"),builder);
                IndexConstant.builderField("regionName", getStringField(data,"regionName"),builder);
                IndexConstant.builderField("organCode", getStringField(data,"organCode"),builder);
                IndexConstant.builderField("organName", getStringField(data,"organName"),builder);
                IndexConstant.builderArrayField("subjectWords", getArrayField(data,"subjectWords"),builder);
                IndexConstant.builderTimeField("collectDate", getDateField(data,"collectDate"),builder);
                IndexConstant.builderField("state", getStringField(data,"state"),builder);
                IndexConstant.builderTimeField("modifyTime", new Date(),builder);
            }
            builder.endObject();
            return builder;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getStringField(JSONObject data, String key){
        if(StringUtils.isNotBlank(data.getString(key))){
            return data.getString(key);
        }
        return "";
    }

    public static Date getDateField(JSONObject data, String key){
        return data.getDate(key);
    }

    public static JSONArray getArrayField(JSONObject data, String key){
        if(StringUtils.isNotBlank(data.getString(key))){
            return data.getJSONArray(key);
        }
        return null;
    }

    public static XContentBuilder builderField(String key, String value, XContentBuilder builder){
        try{
            if(StringUtils.isNotBlank(value)){
                return builder.field(key,value);
            }
        }catch (Exception e){
            log.error("builderField error :" + e);
        }
        return builder;
    }

    public static XContentBuilder builderField(String key, JSONObject value, XContentBuilder builder){
        try{
            if(null!=value && !value.isEmpty()){
                return builder.field(key,value);
            }
        }catch (Exception e){
            log.error("builderField error :" + e);
        }
        return builder;
    }

    public static XContentBuilder builderField(String key, Integer value, XContentBuilder builder){
        try{
            if(null!=value){
                return builder.field(key,value);
            }
        }catch (Exception e){
            log.error("builderField error :" + e);
        }
        return builder;
    }

    public static XContentBuilder builderArrayField(String key, JSONArray value, XContentBuilder builder){
        try{
            if(null!=value){
                return builder.array(key,value.toArray());
            }
        }catch (Exception e){
            log.error("builderArrayField error :" + e);
        }
        return builder;
    }

    public static XContentBuilder builderTimeField(String key, Object value, XContentBuilder builder){
        try{
            if(null!=value){
                return builder.timeField(key,value);
            }
        }catch (Exception e){
            log.error("builderTimeField error :" + e);
        }
        return builder;
    }

    public static JSONArray initQueryFilterArray(JSONObject json){
        JSONArray filter = new JSONArray();
        //部门
        String organCode = json.getString("organCode");
        if(StringUtils.isNotBlank(organCode)){
            filter.add(IndexConstant.initFilterTermObject("organCode",organCode));
        }
        //发布时间范围
        long startTime = json.getLongValue("startTime");
        long endTime = json.getLongValue("endTime");
        int days = json.getIntValue("days");
        if(startTime>0 || endTime>0 || days>0){
            JSONObject dateRange = new JSONObject();
            if(days>0){
                startTime = LocalDateTime.of(LocalDate.now(),LocalTime.MIN).minusDays(days-1)
                        .toInstant(ZoneOffset.of("+8")).toEpochMilli();
            }
            if(startTime>0){
                dateRange.put(RangeEnum.FROM.toString(),startTime);
            }
            if(endTime>0){
                dateRange.put(RangeEnum.TO.toString(),endTime);
            }
            JSONObject range = IndexConstant.initJsonObject(QueryEnum.otherQuery.RANGE.toString(),
                    IndexConstant.initJsonObject("basicReleaseDate",dateRange));
            filter.add(range);
        }
        //状态
        filter.add(IndexConstant.initFilterTermObject("state", ServiceConstant.SYSTEM_ON));
        return filter;
    }

    public static JSONObject initRelationBoolShouldObject(JSONObject json){
        JSONObject bool = new JSONObject();
        JSONArray should = new JSONArray();
        int minimumShouldMatch = 0;
        //关联解读
        String id = json.getString("id");
        if(StringUtils.isNotBlank(id)){
            should.add(initShouldTermObject("policyId",id));
        }
        //父级ID
        JSONArray parentIds = json.getJSONArray("parentId");
        if(null!=parentIds && parentIds.size()>0){
            JSONArray parentShould = new JSONArray();
            parentIds.stream().forEach(parentId -> {
                parentShould.add(initShouldTermObject("id",parentId));
            });
            JSONObject idBool = initJsonObject(BoolQueryEnum.SHOULD.toString(),parentShould);
            idBool.put(BoolQueryEnum.MINIMUM_SHOULD_MATCH.toString(),1);
            idBool.put(BodyParamEnum.Body.BOOST.toString(),50);
            should.add(initJsonObject(BodyParamEnum.Body.BOOL.toString(),idBool));
            minimumShouldMatch ++;
        }
        //标题
        String basicTitle = json.getString("basicTitle");
        if(StringUtils.isNotBlank(basicTitle)){
            should.add(IndexConstant.initJsonObject(QueryEnum.simpleFieldsQuery.QUERY_STRING.toString(),
                    initQueryString(basicTitle,"basicTitle","extendSummary","extendContent")));
        }
        bool.put(BoolQueryEnum.SHOULD.toString(),should);
        bool.put(BoolQueryEnum.MINIMUM_SHOULD_MATCH.toString(),minimumShouldMatch);
        return bool;
    }

    public static Script initAngleViewsIncreaseScript() {
        return new Script(ScriptType.INLINE,"painless","ctx._source.viewTimes ++",Collections.emptyMap());
    }
    
    public static String initEAngleLogIndexName(JSONObject data){
    	if(StringUtils.isBlank(data.getString("createTime"))){
            data.put("createTime",new Date());
    	}
    	Date createTime = data.getDate("createTime");
    	String format = CTools.dateTimeMillis2String(createTime.getTime(),E_ANGLE_LOG_TIME_FORMAT);
        return E_ANGLE_LOG_INDEX + "-" + format;
    }

    public static JSONObject initFilterTermObject(String key,Object value){
        return initJsonObject(QueryEnum.simpleQuery.TERM.toString(),
                initJsonObject(key,value));
    }

    public static JSONObject initShouldTermObject(String key,Object value){
        return initJsonObject(QueryEnum.simpleQuery.TERM.toString(),
                initJsonObject(key,initJsonObject("value",value)));
    }

    public static JSONObject initQueryString(String key,String... fields){
        JSONObject queryString = new JSONObject();
        queryString.put(BodyParamEnum.Body.FIELDS.toString(), fields);
        queryString.put(BodyParamEnum.Body.QUERY.toString(), key);
        return queryString;
    }

    public static JSONObject initJsonObject(String key, Object value){
        JSONObject obj = new JSONObject();
        obj.put(key,value);
        return obj;
    }

    public static JSONArray initJsonArray(JSONObject obj){
        JSONArray array = new JSONArray();
        array.add(obj);
        return array;
    }
}
