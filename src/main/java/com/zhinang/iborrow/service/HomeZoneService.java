package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.HomeZone;
import com.zhinang.iborrow.entity.PageBean;

import java.util.List;

public abstract interface HomeZoneService {
    public abstract void saveHomeZone(HomeZone homeZone);

    public abstract void deleteHomeZone(HomeZone homeZone);

    public abstract List<HomeZone> findHomeZoneList(HomeZone homeZone, PageBean pageBean);

    public abstract Long getHomeZoneCount(HomeZone homeZone);

    public abstract HomeZone findHomeZoneById(int id);

    public abstract List<HomeZone> findHomeZoneByKeyWord(String keyword);
}