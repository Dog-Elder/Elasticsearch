package com.example.elasticsearch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.elasticsearch.entity.Poetries;
import com.example.elasticsearch.mapper.PoetriesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author @Dog_Elder
 * @date 2023-03-19
 */
@Service
public class PoetriesServiceImpl extends ServiceImpl<PoetriesMapper, Poetries> implements IPoetriesService {
    @Resource
    private PoetriesMapper poetriesMapper;


    /**
     * 查询【请填写功能名称】列表
     *
     * @param poetries 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Poetries> list(Poetries poetries) {
        return list(new LambdaQueryWrapper<Poetries>());
    }
}
