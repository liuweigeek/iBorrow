package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.Order;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private BaseDao<Order> baseDao;

    @Override
    public void saveOrder(Order order) {
        baseDao.merge(order);
    }

    @Override
    public void deleteOrder(Order order) {
        baseDao.delete(order);
    }

    @Override
    public List<Order> findOrderList(Order order, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Order");
        if (order != null) {
            if (order.getUser() != null) {
                hql.append(" and user.nickname like ?");
                param.add("%" + order.getUser().getNickname() + "%");
            }
            if (order.getUserAddress() != null) {
                hql.append(" or userAddress.consignee like ?");
                param.add("%" + order.getUserAddress().getConsignee() + "%");
                hql.append(" or userAddress.address like ?");
                param.add("%" + order.getUserAddress().getAddress() + "%");
            }
            if (order.getOrderType() != null) {
                if (order.getOrderType() == Constant.OrderType.BORROW) {
                    hql.append(" and orderType = ?");
                    param.add(order.getOrderType());
                } else if (order.getOrderType() == Constant.OrderType.REFUND) {
                    hql.append(" and orderType = ?");
                    param.add(order.getOrderType());
                }
            }
            if (order.getCreateTime() != null && order.getFinishTime() != null) {
                hql.append(" and createTime between ? and ?");
                param.add(order.getCreateTime());
                param.add(order.getFinishTime());
            }
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getOrderCount(Order order) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Order");
        if (order != null) {
            if (order.getUser() != null) {
                hql.append(" and user.nickname like ?");
                param.add("%" + order.getUser().getNickname() + "%");
            }
            if (order.getUserAddress() != null) {
                hql.append(" or userAddress.consignee like ?");
                param.add("%" + order.getUserAddress().getConsignee() + "%");
                hql.append(" or userAddress.address like ?");
                param.add("%" + order.getUserAddress().getAddress() + "%");
            }
            if (order.getOrderType() != null) {
                if (order.getOrderType() == Constant.OrderType.BORROW) {
                    hql.append(" and orderType = ?");
                    param.add(order.getOrderType());
                } else if (order.getOrderType() == Constant.OrderType.REFUND) {
                    hql.append(" and orderType = ?");
                    param.add(order.getOrderType());
                }
            }
            if (order.getCreateTime() != null && order.getFinishTime() != null) {
                hql.append(" and createTime between ? and ?");
                param.add(order.getCreateTime());
                param.add(order.getFinishTime());
            }
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Order findOrderById(int id) {
        return baseDao.get(Order.class, id);
    }
}