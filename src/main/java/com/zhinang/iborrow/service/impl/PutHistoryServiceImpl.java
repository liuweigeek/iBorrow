package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.PutHistory;
import com.zhinang.iborrow.service.PutHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("putHistoryService")
public class PutHistoryServiceImpl implements PutHistoryService {

    @Resource
    private BaseDao<PutHistory> baseDao;

    @Override
    public void savePutHistory(PutHistory putHistory) {
        this.baseDao.merge(putHistory);
    }

    @Override
    public void deletePutHistory(PutHistory putHistory) {
        this.baseDao.delete(putHistory);
    }

    @Override
    public List<PutHistory> findPutHistoryList(PutHistory putHistory, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from PutHistory");
        if ((putHistory != null) && (putHistory.getProduct() != null)) {
            hql.append(" and product.name like ?");
            param.add("%" + putHistory.getProduct().getName() + "%");
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getPutHistoryCount(PutHistory putHistory) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from PutHistory");
        if ((putHistory != null) && (putHistory.getProduct() != null)) {
            hql.append(" and product.name = ?");
            param.add(putHistory.getProduct().getName());
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public PutHistory findPutHistoryById(int id) {
        return baseDao.get(PutHistory.class, id);
    }
}