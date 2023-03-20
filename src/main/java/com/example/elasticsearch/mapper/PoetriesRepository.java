package com.example.elasticsearch.mapper;

import com.example.elasticsearch.entity.Poetries;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2023-03-19
 */
@Repository
public interface PoetriesRepository extends ElasticsearchRepository<Poetries, Long> {

}
