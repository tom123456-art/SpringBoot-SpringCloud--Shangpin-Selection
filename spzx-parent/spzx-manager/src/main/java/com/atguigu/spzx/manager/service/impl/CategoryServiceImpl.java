package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper ;

    @Override
    public List<Category> findCategoryList(Long id) {

        // 根据分类id查询
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);
        if(!CollectionUtils.isEmpty(categoryList)) {

            // 遍历分类的集合，获取每一个分类数据
            categoryList.forEach(Category -> {

                // 查询该分类下子分类的数量
                int count = categoryMapper.selectCountByParentId(Category.getId());
                if(count > 0) {
                    Category.setHasChildren(true);
                } else {
                    Category.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }
}
