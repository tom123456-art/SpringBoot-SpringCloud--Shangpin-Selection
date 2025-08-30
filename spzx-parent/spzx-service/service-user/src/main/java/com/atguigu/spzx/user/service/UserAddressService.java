package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

//业务接口
public interface UserAddressService {

    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long id);
}