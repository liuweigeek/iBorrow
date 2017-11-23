package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.User;
import java.util.List;

public abstract interface UserService {
	public abstract void saveUser(User user);

	public abstract void deleteUser(User user);

	public abstract List<User> findUserList(User user, PageBean pageBean);

	public abstract Long getUserCount(User user);

	public abstract User findUserById(int id);

	public abstract User login(User user);

	public abstract boolean existUserWithNickname(String nickname);

	public abstract User getUserByNickname(String nickname);

	public abstract boolean existUserWithWeixinOpenId(String openId);
	
	public abstract boolean existUserWithVipId(String vipId);

	public abstract User getUserByWeixinOpenId(String openId);
	
	public abstract User getUserByVipId(String vipId);
}