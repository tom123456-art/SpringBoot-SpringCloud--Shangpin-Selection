package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        //1. 获取输入验证码和存储到redis的key名称，loginDto获取到
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //2. 根据获取的key，查询redis里面存储验证码
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //3. 比较输入的验证码和redis存储验证码是否一至
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)) {
            //4. 如果不一致，提示用户效验失败
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //5. 如果一致，删除redis里面的验证码
        redisTemplate.delete("user:validate" + key);

        // 根据用户名查询用户
        String userName= loginDto.getUserName();

        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        if(sysUser == null) {
 //           throw new RuntimeException("用户名不存在") ;
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 验证密码是否正确
        String database_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());

        if(!input_password.equals(sysUser.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 生成令牌，保存数据到Redis中
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        redisTemplate.opsForValue().set("user:login" + token , JSON.toJSONString(sysUser) , 7 , TimeUnit.DAYS);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);

        // 返回
        return loginVo;
    }


    //获取当前登录信息
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson,SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    //用户条件分页查询
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto) ;
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        //用户名不能重复
        String userName =sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(dbSysUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //输入密码进行加密
        String mad5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(mad5_password);
        //设置status值
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }

    //用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    //用户删除
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //根据用户userid删除用户之前分配的角色
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId()) ;

        //重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }
}
