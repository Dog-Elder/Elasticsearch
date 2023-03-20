package com.example.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.elasticsearch.entity.Poetries;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author @Dog_Elder
 * @date 2023-03-19
 */
public interface IPoetriesService extends IService<Poetries>
{

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param poetries 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Poetries> list(Poetries poetries);

}
