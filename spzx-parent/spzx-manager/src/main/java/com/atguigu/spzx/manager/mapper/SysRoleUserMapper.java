package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    //删除之前分配的角色
    void deleteByUserId(Long userId);

    //重新分配
    void doAssign(Long userId, Long roleId);

    List<Long> selectRoleIdsByUserId(Long userId);
}
