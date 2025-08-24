package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
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

        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 7 , TimeUnit.DAYS);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);

        // 返回
        return loginVo;
    }
}
