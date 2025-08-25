package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    //角色列表方法
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    //角色添加
    void save(SysRole sysRole);

    //角色修改
    void update(SysRole sysRole);

    //角色删除
    void delete(Long roleId);
}
