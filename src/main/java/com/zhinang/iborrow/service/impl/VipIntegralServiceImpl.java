package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipIntegral;
import com.zhinang.iborrow.service.VipIntegralService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("vipIntegralService")
public class VipIntegralServiceImpl implements VipIntegralService {

    @Resource
    private BaseDao<VipIntegral> baseDao;

    @Override
    public void saveVipIntegral(VipIntegral vipIntegral) {
        baseDao.merge(vipIntegral);
    }

    @Override
    public void deleteVipIntegral(VipIntegral vipIntegral) {
        baseDao.delete(vipIntegral);
    }

    @Override
    public List<VipIntegral> findVipIntegralList(VipIntegral vipIntegral, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from VipIntegral");

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getVipIntegralCount(VipIntegral vipIntegral) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from VipIntegral");

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public VipIntegral findVipIntegralById(int id) {
        return baseDao.get(VipIntegral.class, id);
    }
}