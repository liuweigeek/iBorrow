package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.OrderItem;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface OrderItemService {

	public abstract void saveOrderItem(OrderItem orderItem);

	public abstract void deleteOrderItem(OrderItem orderItem);

	public abstract List<OrderItem> findOrderItemList(OrderItem orderItem, PageBean pageBean);

	public abstract Long getOrderItemCount(OrderItem orderItem);

	public abstract OrderItem findOrderItemById(int id);
}