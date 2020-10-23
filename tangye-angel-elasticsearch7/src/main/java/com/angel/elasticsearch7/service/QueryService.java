package com.angel.elasticsearch7.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.script.mustache.SearchTemplateRequest;

public interface QueryService {

    SearchTemplateRequest initSearchTemplateRequest(String... indices);

    JSONObject initAngleSearchScript(JSONObject json);
}
