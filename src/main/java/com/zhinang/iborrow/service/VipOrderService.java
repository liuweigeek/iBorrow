package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipOrder;
import java.util.List;

public abstract interface VipOrderService {
	public abstract void saveVipOrder(VipOrder vipOrder);

	public abstract void deleteVipOrder(VipOrder vipOrder);

	public abstract List<VipOrder> findVipOrderList(VipOrder vipOrder, PageBean pageBean);

	public abstract Long getVipOrderCount(VipOrder vipOrder);

	public abstract VipOrder findVipOrderById(int id);
}