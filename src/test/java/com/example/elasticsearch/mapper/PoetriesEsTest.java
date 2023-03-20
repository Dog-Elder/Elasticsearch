package com.example.elasticsearch.mapper;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.elasticsearch.entity.Poetries;
import com.example.elasticsearch.service.IPoetriesService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: Elasticsearch
 * @description:
 * @author: @Dog_Elder
 * @create: 2023-03-20 15:45
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoetriesEsTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    private IPoetriesService poetriesService;
    @Resource
    private PoetriesRepository poetriesRepository;


    /**
     * 数据库中数据全部添加到ES中
     **/
    @Test
    public void addALL() {
        List<Poetries> list = poetriesService.list();
        poetriesRepository.saveAll(list);
        System.out.println("数据添加成功");
    }

    /**
     * 高亮查询
     **/
    @Test
    public void highlight() {
        List<Poetries> select = select(1, 20, "深秋");
        JSONArray objects = JSONUtil.parseArray(select);
        System.out.println(objects);
    }

    public List<Poetries> select(int page, int limit, String c) {
        //查询策略
//        TermQueryBuilder contentBuilder = QueryBuilders.termQuery("content", c);
//        TermQueryBuilder titleBuilder = QueryBuilders.termQuery("title", "长亭");
        //查询策略 这种更加灵活
        BoolQueryBuilder must = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("content", c))
                .must(QueryBuilders.termQuery("title", "长亭"));
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*");
        //综合查询方案
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //查询策略
                .withQuery(must)
                //突出显示字段
                .withHighlightFields(highlightBuilder)
                //page 就是页数  但是ES第一页默认为0
                //limit 展示条数
                .withPageable(PageRequest.of(page - 1, limit))
                .build();

        SearchHits<Poetries> search = elasticsearchRestTemplate.search(build, Poetries.class);
        List<SearchHit<Poetries>> searchHits = search.getSearchHits();
        List<Poetries> list = new ArrayList<>();
        for (SearchHit<Poetries> hit : searchHits) {
            Map<String, List<String>> map = hit.getHighlightFields();
            List<String> content = map.get("content");
            if (!CollectionUtils.isEmpty(content)) {
                hit.getContent().setContent(content.get(0));
            }
            list.add(hit.getContent());
        }
        return list;
    }
}
