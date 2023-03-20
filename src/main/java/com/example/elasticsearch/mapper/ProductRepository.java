package com.example.elasticsearch.mapper;

import com.example.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {
    //根据title 查询全部    自定义根据方法名来进行条件拼接查询 其实用处感觉很小
    public List<Product> findAllByTitleOrTitle(String title,String title2);
}