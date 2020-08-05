package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.Notification;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.NotificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private BaseDao<Notification> baseDao;

    @Override
    public void saveNotification(Notification notification) {
        baseDao.merge(notification);
    }

    @Override
    public void deleteNotification(Notification notification) {
        baseDao.delete(notification);
    }

    @Override
    public List<Notification> findNotificationList(Notification notification, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Notification");

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getNotificationCount(Notification notification) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Notification");

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Notification findNotificationById(int id) {
        return baseDao.get(Notification.class, id);
    }
}