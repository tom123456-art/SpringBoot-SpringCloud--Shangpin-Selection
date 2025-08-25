package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

public interface SysRoleService {
    //角色列表方法
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    //角色添加
    void saveSysRole(SysRole sysRole);

    //角色修改
    void updateSysRole(SysRole sysRole);

    //角色删除
    void deleteById(Long roleId);
}
