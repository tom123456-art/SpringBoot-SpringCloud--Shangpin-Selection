package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    //列表
    List<Product> findByPage(ProductDto productDto);

    // 保存商品数据
    void save(Product product);

    // 根据id查询商品数据
    Product findProductById(Long id);

    //修改product
    void updateById(Product product);

    // 根据id删除商品基本数据
    void deleteById(Long id);
}