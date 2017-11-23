package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.Navi;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.NaviService;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("naviService")
public class NaviServiceImpl implements NaviService {

	@Resource
	private BaseDao<Navi> baseDao;

	@Override
    public void saveNavi(Navi navi) {
		baseDao.merge(navi);
	}

	@Override
    public void deleteNavi(Navi navi) {
		baseDao.delete(navi);
	}

	@Override
    public List<Navi> findNaviList(Navi navi, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Navi");
		if (pageBean != null)
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);

		return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public Long getNaviCount(Navi navi) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Navi");
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public Navi findNaviById(int id) {
		return baseDao.get(Navi.class, id);
	}
}