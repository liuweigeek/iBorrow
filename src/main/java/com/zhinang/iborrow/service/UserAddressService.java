package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.UserAddress;

import java.util.List;

public abstract interface UserAddressService {
    public abstract void saveUserAddress(UserAddress userAddress);

    public abstract void deleteUserAddress(UserAddress userAddress);

    public abstract List<UserAddress> findUserAddressList(UserAddress userAddress, PageBean pageBean);

    public abstract Long getUserAddressCount(UserAddress userAddress);

    public abstract UserAddress findUserAddressById(int id);

    public abstract List<UserAddress> findUserAddressByUser(User user);
}