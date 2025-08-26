package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
//查询角色分配过的菜单id列表
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    // 根据角色的id删除其所对应的菜单数据
    void deleteByRoleId(Long roleId);

    // 保存分配数据
    void doAssign(AssginMenuDto assginMenuDto);

    void updateSysRoleMenuIsHalf(Long menuId);
}
