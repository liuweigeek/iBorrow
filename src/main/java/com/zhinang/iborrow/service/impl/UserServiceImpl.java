package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private BaseDao<User> baseDao;

    @Override
    public void saveUser(User user) {
        baseDao.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        baseDao.delete(user);
    }

    @Override
    public List<User> findUserList(User user, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from User");
        if ((user != null) && StringUtil.isNotEmpty(user.getNickname())) {
            hql.append(" and nickname like ?");
            param.add("%" + user.getNickname() + "%");
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getUserCount(User user) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from User");
        if ((user != null) && (StringUtil.isNotEmpty(user.getNickname()))) {
            hql.append(" and nickname like ?");
            param.add("%" + user.getNickname() + "%");
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public User findUserById(int id) {
        return baseDao.get(User.class, id);
    }

    @Override
    public User login(User user) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from User u where u.phone=? and u.password=?");
        param.add(user.getPhone());
        param.add(user.getPassword());
        return baseDao.get(hql.toString(), param);
    }

    @Override
    public boolean existUserWithNickname(String nickname) {
        String hql = "select count(*) from User where nickname=?";
        long count = baseDao.count(hql, new Object[]{nickname});

        return (count > 0);
    }

    @Override
    public User getUserByNickname(String nickname) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from User");
        hql.append(" where nickname = ?");
        param.add(nickname);
        return baseDao.get(hql.toString(), param);
    }

    @Override
    public boolean existUserWithWeixinOpenId(String wx_openid) {
        String hql = "select count(*) from User where wx_openid=?";
        long count = baseDao.count(hql, new Object[]{wx_openid});

        return (count > 0);
    }

    @Override
    public User getUserByWeixinOpenId(String wx_openid) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from User");
        if (StringUtil.isNotEmpty(wx_openid)) {
            hql.append(" and wx_openid = ?");
            param.add(wx_openid);
        }
        return baseDao.get(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public boolean existUserWithVipId(String vipId) {
        String hql = "select count(*) from User where vipId=?";
        long count = baseDao.count(hql, new Object[]{vipId});

        return (count > 0);
    }

    @Override
    public User getUserByVipId(String vipId) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from User");
        if (StringUtil.isNotEmpty(vipId)) {
            hql.append(" and vipId = ?");
            param.add(vipId);
        }
        return baseDao.get(hql.toString().replaceFirst("and", "where"), param);
    }
}