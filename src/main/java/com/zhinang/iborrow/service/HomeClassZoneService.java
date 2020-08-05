package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.HomeClassZone;
import com.zhinang.iborrow.entity.PageBean;

import java.util.List;

public abstract interface HomeClassZoneService {
    public abstract void saveHomeClassZone(HomeClassZone homeClassZone);

    public abstract void deleteHomeClassZone(HomeClassZone homeClassZone);

    public abstract List<HomeClassZone> findHomeClassZoneList(HomeClassZone homeClassZone, PageBean pageBean);

    public abstract Long getHomeClassZoneCount(HomeClassZone homeClassZone);

    public abstract HomeClassZone findHomeClassZoneById(int id);
}