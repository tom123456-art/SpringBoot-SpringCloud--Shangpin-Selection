package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;


import java.util.List;

@Mapper
public interface CategoryMapper {

    // 根据分类id查询
    List<Category> selectCategoryByParentId(Long id);

    //判断每个分类是否有下一次分类
    int selectCountByParentId(Long id);

    // 查询所有分类
    List<Category> findAll();

    //批量保存的方法
    void batchInsert(List<CategoryExcelVo> categoryList);
}
