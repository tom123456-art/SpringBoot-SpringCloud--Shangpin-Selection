package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {

    void save(UserInfo userInfo);

    UserInfo selectByUsername(@Param("username") String username);

}