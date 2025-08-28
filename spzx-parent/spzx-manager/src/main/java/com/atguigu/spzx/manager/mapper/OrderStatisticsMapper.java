package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {

    void insert(OrderStatistics orderStatistics);

    // 查询统计结果数据
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}