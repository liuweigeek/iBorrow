package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.Wish;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.WishService;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wishService")
public class WishServiceImpl implements WishService {

	@Resource
	private BaseDao<Wish> baseDao;

	@Override
    public void saveWish(Wish wish) {
		baseDao.merge(wish);
	}

	@Override
    public void deleteWish(Wish wish) {
		baseDao.delete(wish);
	}

	@Override
    public List<Wish> findWishList(Wish wish, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Wish");
		if (wish != null) {
			if (wish.getUser() != null) {
				hql.append(" and user = ?");
				param.add(wish.getUser());
			}
			if (wish.getText() != null) {
				hql.append(" and text = ?");
				param.add(wish.getText());
			}
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getWishCount(Wish wish) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Wish");
		if (wish != null) {
			if (wish.getUser() != null) {
				hql.append(" and user = ?");
				param.add(wish.getUser());
			}
			if (wish.getText() != null) {
				hql.append(" and text = ?");
				param.add(wish.getText());
			}
		}
		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public Wish findWishById(int id) {
		return baseDao.get(Wish.class, id);
	}
}