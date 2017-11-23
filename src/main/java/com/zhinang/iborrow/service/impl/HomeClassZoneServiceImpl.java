package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.HomeClassZone;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.HomeClassZoneService;
import com.zhinang.iborrow.util.StringUtil;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("homeClassZoneService")
public class HomeClassZoneServiceImpl implements HomeClassZoneService {

	@Resource
	private BaseDao<HomeClassZone> baseDao;

	@Override
    public void saveHomeClassZone(HomeClassZone homeClassZone) {
		baseDao.merge(homeClassZone);
	}

	@Override
    public void deleteHomeClassZone(HomeClassZone homeClassZone) {
		baseDao.delete(homeClassZone);
	}

	@Override
    public List<HomeClassZone> findHomeClassZoneList(HomeClassZone homeClassZone, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from HomeClassZone");
		if (homeClassZone != null && StringUtil.isNotEmpty(homeClassZone.getName())) {
			hql.append(" and name like ?");
			param.add("%" + homeClassZone.getName() + "%");
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getHomeClassZoneCount(HomeClassZone homeClassZone) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from HomeClassZone");
		if ((homeClassZone != null) && StringUtil.isNotEmpty(homeClassZone.getName())) {
			hql.append(" and name like ?");
			param.add("%" + homeClassZone.getName() + "%");
		}

		return this.baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public HomeClassZone findHomeClassZoneById(int id) {
		return baseDao.get(HomeClassZone.class, id);
	}
}