package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    //添加
    void save(ProductSku productSku);

    // 根据商品的id查询sku数据
    List<ProductSku> findProductSkuByProductId(Long id);

    // 修改商品的sku数据
    void updateById(ProductSku productSku);

    // 根据商品id删除商品的sku数据
    void deleteByProductId(Long id);
}
