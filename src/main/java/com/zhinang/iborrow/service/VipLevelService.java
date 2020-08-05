package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipLevel;

import java.util.List;

public abstract interface VipLevelService {
    public abstract void saveVipLevel(VipLevel vipLevel);

    public abstract void deleteVipLevel(VipLevel vipLevel);

    public abstract List<VipLevel> findVipLevelList(VipLevel vipLevel, PageBean pageBean);

    public abstract Long getVipLevelCount(VipLevel vipLevel);

    public abstract VipLevel findVipLevelById(int id);
}