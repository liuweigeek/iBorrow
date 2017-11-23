package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.HomeClassify;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.service.HomeClassifyService;
import com.zhinang.iborrow.util.StringUtil;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("homeClassifyService")
public class HomeClassifyServiceImpl implements HomeClassifyService {

	@Resource
	private BaseDao<HomeClassify> baseDao;

	@Override
    public void saveHomeClassify(HomeClassify homeClassify) {
		baseDao.merge(homeClassify);
	}

	@Override
    public void deleteHomeClassify(HomeClassify homeClassify) {
		baseDao.delete(homeClassify);
	}

	@Override
    public List<HomeClassify> findHomeClassifyList(HomeClassify homeClassify, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from HomeClassify");
		if (homeClassify != null) {
			if (StringUtil.isNotEmpty(homeClassify.getName())) {
                hql.append(" and name like ?");
                param.add("%" + homeClassify.getName() + "%");
            }
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getHomeClassifyCount(HomeClassify homeClassify) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from HomeClassify");
        if (homeClassify != null) {
            if (StringUtil.isNotEmpty(homeClassify.getName())) {
                hql.append(" and name like ?");
                param.add("%" + homeClassify.getName() + "%");
            }
        }

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public HomeClassify findHomeClassifyById(int id) {
		return baseDao.get(HomeClassify.class, id);
	}

    @Override
    public List<HomeClassify> findHomeClassifyByKeyWord(String keyword) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from HomeClassify");
        hql.append(" where name like ?");
        param.add("%" + keyword + "%");

        return baseDao.find(hql.toString(), param);
    }
}