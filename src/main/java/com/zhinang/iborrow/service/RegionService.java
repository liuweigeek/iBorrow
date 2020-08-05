package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Region;

import java.util.List;

public abstract interface RegionService {
    public abstract void saveRegion(Region region);

    public abstract void deleteRegion(Region region);

    public abstract List<Region> findRegionList(Region region, PageBean pageBean);

    public abstract Long getRegionCount(Region region);

    public abstract Region findRegionById(int id);

    public abstract List<Region> findRegionByParentId(int id);

}