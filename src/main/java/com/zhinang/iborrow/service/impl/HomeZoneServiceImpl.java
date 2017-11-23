package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.HomeZone;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.HomeZoneService;
import com.zhinang.iborrow.util.StringUtil;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("homeZoneService")
public class HomeZoneServiceImpl implements HomeZoneService {

	@Resource
	private BaseDao<HomeZone> baseDao;

	@Override
    public void saveHomeZone(HomeZone homeZone) {
		baseDao.merge(homeZone);
	}

	@Override
    public void deleteHomeZone(HomeZone homeZone) {
		baseDao.delete(homeZone);
	}

	@Override
    public List<HomeZone> findHomeZoneList(HomeZone homeZone, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from HomeZone");
		if ((homeZone != null) && StringUtil.isNotEmpty(homeZone.getName())) {
			hql.append(" and name like ?");
			param.add("%" + homeZone.getName() + "%");
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getHomeZoneCount(HomeZone homeZone) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from HomeZone");
		if ((homeZone != null) && StringUtil.isNotEmpty(homeZone.getName())) {
			hql.append(" and name like ?");
			param.add("%" + homeZone.getName() + "%");
		}

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public HomeZone findHomeZoneById(int id) {
		return baseDao.get(HomeZone.class, id);
	}

	@Override
	public List<HomeZone> findHomeZoneByKeyWord(String keyword) {
		List<Object> param = new LinkedList<>();
		StringBuffer hql = new StringBuffer("from HomeZone");
		hql.append(" where name like ?");
		param.add("%" + keyword + "%");

		return baseDao.find(hql.toString(), param);
	}
}