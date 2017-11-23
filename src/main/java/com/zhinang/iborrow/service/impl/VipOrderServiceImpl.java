package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipOrder;
import com.zhinang.iborrow.service.VipOrderService;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("vipOrderService")
public class VipOrderServiceImpl implements VipOrderService {

	@Resource
	private BaseDao<VipOrder> baseDao;

	@Override
    public void saveVipOrder(VipOrder vipOrder) {
		baseDao.merge(vipOrder);
	}

	@Override
    public void deleteVipOrder(VipOrder vipOrder) {
		baseDao.delete(vipOrder);
	}

	@Override
    public List<VipOrder> findVipOrderList(VipOrder vipOrder, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from VipOrder");

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getVipOrderCount(VipOrder vipOrder) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from VipOrder");

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public VipOrder findVipOrderById(int id) {
		return baseDao.get(VipOrder.class, id);
	}
}