package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService ;

    //4.角色删除
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId) ;
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //3.角色修改方法
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //2.角色添加方法
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole) ;
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //1.角色列表方法
    //current：当前页  limit：每页记录数
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable(value = "current") Integer current,
                             @PathVariable(value = "limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        //pageHelper插件实现分页查询
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto , current , limit) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }
}
