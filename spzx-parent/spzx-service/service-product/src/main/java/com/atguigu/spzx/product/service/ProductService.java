package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<ProductSku> selectProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);

    /**
     * 校验并原子扣减库存（防超卖）
     * @param skuId 商品skuId
     * @param num 购买数量
     * @return true=扣减成功 false=库存不足
     */
    boolean checkAndDeductStock(Long skuId, Integer num);
}
