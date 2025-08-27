package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    //列表
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    //添加
    void save(Brand brand);

    //修改
    void updateById(Brand brand);

    //删除
    void deleteById(Long id);

    //查询所有品牌
    List<Brand> findAll();
}
