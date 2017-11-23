package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.UserAddress;
import com.zhinang.iborrow.service.UserAddressService;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

	@Resource
	private BaseDao<UserAddress> baseDao;

	@Override
    public void saveUserAddress(UserAddress userAddress) {
		baseDao.merge(userAddress);
	}

	@Override
    public void deleteUserAddress(UserAddress userAddress) {
		baseDao.delete(userAddress);
	}

	@Override
    public List<UserAddress> findUserAddressList(UserAddress userAddress, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from UserAddress");
		if ((userAddress != null) && (userAddress.getPhone() != null)) {
			hql.append(" and phone = ?");
			param.add(userAddress.getPhone());
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}			
	}

	@Override
    public Long getUserAddressCount(UserAddress userAddress) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from UserAddress");
		if ((userAddress != null) && (userAddress.getPhone() != null)) {
			hql.append(" and phone = ?");
			param.add(userAddress.getPhone());
		}

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public UserAddress findUserAddressById(int id) {
		return baseDao.get(UserAddress.class, id);
	}

	@Override
	public List<UserAddress> findUserAddressByUser(User user) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from UserAddress");
		hql.append(" where user = ?");
		param.add(user);

		return baseDao.find(hql.toString(), param);
	}
}