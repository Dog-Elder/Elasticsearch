package com.example.elasticsearch.mapper;

import com.example.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
    //根据title 查询全部
    public List<Product> findAllByTitleOrTitle(String title,String title2);
}