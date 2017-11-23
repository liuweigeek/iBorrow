package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.Order;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface OrderService {
	public abstract void saveOrder(Order order);

	public abstract void deleteOrder(Order order);

	public abstract List<Order> findOrderList(Order order, PageBean pageBean);

	public abstract Long getOrderCount(Order order);

	public abstract Order findOrderById(int id);
}