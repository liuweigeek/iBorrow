package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipLevel;
import com.zhinang.iborrow.service.VipLevelService;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("vipLevelService")
public class VipLevelServiceImpl implements VipLevelService {

	@Resource
	private BaseDao<VipLevel> baseDao;

	@Override
    public void saveVipLevel(VipLevel vipLevel) {
		baseDao.merge(vipLevel);
	}

	@Override
    public void deleteVipLevel(VipLevel vipLevel) {
		baseDao.delete(vipLevel);
	}

	@Override
    public List<VipLevel> findVipLevelList(VipLevel vipLevel, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from VipLevel");

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getVipLevelCount(VipLevel vipLevel) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from VipLevel");

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public VipLevel findVipLevelById(int id) {
		return baseDao.get(VipLevel.class, id);
	}
}