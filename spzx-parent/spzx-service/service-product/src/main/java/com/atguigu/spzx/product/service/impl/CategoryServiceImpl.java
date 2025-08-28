package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// 接口实现类
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public List<Category> selectOneCategory() {

        // 从Redis缓存中查询所有的一级分类数据
        String categoryOneJSON = redisTemplate.opsForValue().get("category:one");
        if(!StringUtils.hasText(categoryOneJSON)) {
            List<Category> existCategoryList = JSON.parseArray(categoryOneJSON, Category.class);
//            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return existCategoryList;
        }

        List<Category> categoryList = categoryMapper.selectOneCategory();
//        log.info("从数据库中查询到了所有的一级分类数据");
        redisTemplate.opsForValue().set("category:one" , JSON.toJSONString(categoryList) , 7 , TimeUnit.DAYS);
        return categoryList ;
    }

    @Cacheable(value = "category" , key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        List<Category> allCategoryList = categoryMapper.findAll();
        //全部一级分类
        List<Category> oneCategoryList = allCategoryList.stream().filter(item ->
                item.getParentId().longValue() == 0).collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = allCategoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);

                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = allCategoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}