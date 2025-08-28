package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

// 业务接口
public interface CategoryService {

    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}