package com.example.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.elasticsearch.entity.Poetries;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2023-03-19
 */
public interface PoetriesMapper extends BaseMapper<Poetries>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Poetries selectPoetriesById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param poetries 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Poetries> selectPoetriesList(Poetries poetries);

    /**
     * 新增【请填写功能名称】
     * 
     * @param poetries 【请填写功能名称】
     * @return 结果
     */
    public int insertPoetries(Poetries poetries);

    /**
     * 修改【请填写功能名称】
     * 
     * @param poetries 【请填写功能名称】
     * @return 结果
     */
    public int updatePoetries(Poetries poetries);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePoetriesById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePoetriesByIds(String[] ids);
}
