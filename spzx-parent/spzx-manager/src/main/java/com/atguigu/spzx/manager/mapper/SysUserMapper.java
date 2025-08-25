package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    //用户条件分页查询
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //用户添加
    void save(SysUser sysUser);

    //用户修改
    void update(SysUser sysUser);

    void delete(Long userId);
}
