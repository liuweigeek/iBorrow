package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.OrderItem;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("OrderItemService")
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private BaseDao<OrderItem> baseDao;

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        baseDao.merge(orderItem);
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        baseDao.delete(orderItem);
    }

    @Override
    public List<OrderItem> findOrderItemList(OrderItem orderItem, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from OrderItem");
        if ((orderItem != null) && (orderItem.getProduct() != null)) {
            hql.append(" and product.name like ?");
            param.add("%" + orderItem.getProduct().getName() + "%");
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getOrderItemCount(OrderItem orderItem) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from OrderItem");
        if ((orderItem != null) && (orderItem.getProduct() != null)) {
            hql.append(" and product.name = ?");
            param.add(orderItem.getProduct().getName());
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public OrderItem findOrderItemById(int id) {
        return baseDao.get(OrderItem.class, id);
    }
}