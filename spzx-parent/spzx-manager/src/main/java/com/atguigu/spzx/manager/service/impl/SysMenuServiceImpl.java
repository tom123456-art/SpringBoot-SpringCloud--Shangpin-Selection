package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    //菜单列表
    @Autowired
    private SysMenuMapper sysMenuMapper ;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper ;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.findAll() ;
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        //返回的list集合封装要求数据格式
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList); //构建树形数据
        return treeList;
    }

    //菜单添加
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenu(sysMenu) ;
    }

    private void updateSysRoleMenu(SysMenu sysMenu) {
        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu != null) {
            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId()) ;
            // 递归调用
            updateSysRoleMenu(parentMenu) ;
        }
    }

    //菜单修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    //菜单删除
    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.selectCountById(id);  // 先查询是否存在子菜单，如果存在不允许进行删除
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.delete(id);		// 不存在子菜单直接删除
    }

    //查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {

        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();          // 获取当前登录用户的id

        //构建树形数据
        List<SysMenu> sysMenuList = MenuHelper.buildTree(sysMenuMapper.findMenusByUserId(userId));
        List<SysMenuVo> sysMenuVos = this.buildMenus(sysMenuList);
        return sysMenuVos;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
