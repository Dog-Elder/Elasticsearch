package com.example.elasticsearch.mapper;

import com.example.elasticsearch.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
@Component
public class CustomProductRepository {
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    public List<Product> select(int page, int limit, String c) {
        //查询策略
        TermQueryBuilder builder = QueryBuilders.termQuery("title", c);
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*");
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(builder)
//                .withHighlightFields(highlightBuilder)
//                .withPageable(PageRequest.of(page - 1, limit))
                .build();

        SearchHits<Product> search = elasticsearchTemplate.search(build, Product.class);
        List<SearchHit<Product>> searchHits = search.getSearchHits();
        List<Product> list = new ArrayList<>();
        for (SearchHit<Product> hit : searchHits) {
            List<Object> sortValues = hit.getSortValues();
            System.out.println("sortValues = " + sortValues);
        }
        return null;
    }
}
