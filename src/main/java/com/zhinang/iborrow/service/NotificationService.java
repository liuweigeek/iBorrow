package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.Notification;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface NotificationService {
	public abstract void saveNotification(Notification notification);

	public abstract void deleteNotification(Notification notification);

	public abstract List<Notification> findNotificationList(Notification notification, PageBean pageBean);

	public abstract Long getNotificationCount(Notification notification);

	public abstract Notification findNotificationById(int id);
}