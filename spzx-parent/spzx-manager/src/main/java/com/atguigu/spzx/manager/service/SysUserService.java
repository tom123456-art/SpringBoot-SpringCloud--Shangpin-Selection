package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    //用户退出
    void logout(String token);

    //用户条件分页查询
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    //用户添加
    void saveSysUser(SysUser sysUser);

    //用户修改
    void updateSysUser(SysUser sysUser);

    //用户删除
    void deleteById(Long userId);
}
