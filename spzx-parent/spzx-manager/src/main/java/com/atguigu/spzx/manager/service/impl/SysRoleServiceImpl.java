package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //角色列表方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页相关的参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageinfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    //角色修改
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    //角色删除
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }
}
