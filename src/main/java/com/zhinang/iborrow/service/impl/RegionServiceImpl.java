package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Region;
import com.zhinang.iborrow.service.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("regionService")
public class RegionServiceImpl implements RegionService {

    @Resource
    private BaseDao<Region> baseDao;

    @Override
    public void saveRegion(Region region) {
        baseDao.merge(region);
    }

    @Override
    public void deleteRegion(Region region) {
        baseDao.delete(region);
    }

    @Override
    public List<Region> findRegionList(Region region, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Region");
        if ((region != null) && (region.getParentId() != 0)) {
            hql.append(" and parentId = ?");
            param.add(region.getParentId());
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }

    }

    @Override
    public Long getRegionCount(Region region) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Region");
        if ((region != null) && (region.getParentId() != 0)) {
            hql.append(" and parentId = ?");
            param.add(region.getParentId());
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Region findRegionById(int id) {
        return baseDao.get(Region.class, id);
    }

    @Override
    public List<Region> findRegionByParentId(int id) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Region");
        hql.append(" where parentId = ?");
        param.add(id);
        return baseDao.find(hql.toString(), param);
    }

}