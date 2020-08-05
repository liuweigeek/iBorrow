package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipIntegral;

import java.util.List;

public abstract interface VipIntegralService {
    public abstract void saveVipIntegral(VipIntegral vipIntegral);

    public abstract void deleteVipIntegral(VipIntegral vipIntegral);

    public abstract List<VipIntegral> findVipIntegralList(VipIntegral vipIntegral, PageBean pageBean);

    public abstract Long getVipIntegralCount(VipIntegral vipIntegral);

    public abstract VipIntegral findVipIntegralById(int id);
}