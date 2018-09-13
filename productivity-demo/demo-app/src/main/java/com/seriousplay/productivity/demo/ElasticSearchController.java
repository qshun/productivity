package com.seriousplay.productivity.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elasticsearch")
public class ElasticSearchController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping("/test01")
    public void test01(){

    }
}
