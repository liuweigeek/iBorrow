package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipCard;
import com.zhinang.iborrow.service.VipCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("vipCardService")
public class VipCardServiceImpl implements VipCardService {

    @Resource
    private BaseDao<VipCard> baseDao;

    @Override
    public void saveVipCard(VipCard vipCard) {
        baseDao.merge(vipCard);
    }

    @Override
    public void deleteVipCard(VipCard vipCard) {
        baseDao.delete(vipCard);
    }

    @Override
    public List<VipCard> findVipCardList(VipCard vipCard, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from VipCard");

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getVipCardCount(VipCard vipCard) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from VipCard");

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public VipCard findVipCardById(int id) {
        return baseDao.get(VipCard.class, id);
    }
}