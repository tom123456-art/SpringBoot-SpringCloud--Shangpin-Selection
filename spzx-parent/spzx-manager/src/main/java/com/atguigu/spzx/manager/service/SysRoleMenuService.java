package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    //查询所有菜单和查询角色分配过的菜单id列表
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    //删除角色之前分配过的菜单
    void deleteByRoleId(Long roleId);

    //分配菜单
    void doAssign(AssginMenuDto assginMenuDto);
}
