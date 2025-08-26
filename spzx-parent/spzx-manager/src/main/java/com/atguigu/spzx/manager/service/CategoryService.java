package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findCategoryList(Long id);
}
