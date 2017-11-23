package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Payment;
import com.zhinang.iborrow.service.PaymentService;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private BaseDao<Payment> baseDao;

	@Override
    public void savePayment(Payment payment) {
		baseDao.merge(payment);
	}

	@Override
    public void deletePayment(Payment payment) {
		baseDao.delete(payment);
	}

	@Override
    public List<Payment> findPaymentList(Payment payment, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Payment");

		if (payment != null) {
		    if (payment.getUser() != null) {
                hql.append(" and user.nickname like ?");
                param.add("%" + payment.getUser().getNickname() + "%");

				if (payment.getTransaction_id() != null) {
					hql.append(" or transaction_id like ?");
					param.add("%" + payment.getTransaction_id() + "%");
				}
				if (payment.getOut_trade_no() != null) {
					hql.append(" or out_trade_no like ?");
					param.add("%" + payment.getOut_trade_no() + "%");
				}
		    }
            if (payment.getTime_start() != null) {
                hql.append(" and time_start >= ?");
                param.add(payment.getTime_start());
            }
            if (payment.getTime_expire() != null) {
                hql.append(" and time_start <= ?");
                param.add(payment.getTime_expire());
            }
            /*if (payment.getTime_start() != null && payment.getTime_expire() != null) {
                hql.append(" and time_start between ? and ?");
                param.add(payment.getTime_start());
                param.add(payment.getTime_expire());
            }*/
        }

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
    public Long getPaymentCount(Payment payment) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Payment");

        if (payment != null) {
            if (payment.getUser() != null) {
                hql.append(" and user.nickname like ?");
                param.add("%" + payment.getUser().getNickname() + "%");

				if (payment.getTransaction_id() != null) {
					hql.append(" or transaction_id like ?");
					param.add("%" + payment.getTransaction_id() + "%");
				}
				if (payment.getOut_trade_no() != null) {
					hql.append(" or out_trade_no like ?");
					param.add("%" + payment.getOut_trade_no() + "%");
				}
            }
            if (payment.getTime_start() != null) {
                hql.append(" and time_start >= ?");
                param.add(payment.getTime_start());
            }
            if (payment.getTime_expire() != null) {
                hql.append(" and time_start <= ?");
                param.add(payment.getTime_expire());
            }
            /*if (payment.getTime_start() != null && payment.getTime_expire() != null) {
                hql.append(" and time_start between ? and ?");
                param.add(payment.getTime_start());
                param.add(payment.getTime_expire());
            }*/
        }

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public Payment findPaymentById(int id) {
		return baseDao.get(Payment.class, id);
	}

	@Override
	public Payment findPaymentByoutTradeNo(String outTradeNo) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Payment");
		hql.append(" where out_trade_no = ?");
		param.add(outTradeNo);
		return baseDao.get(hql.toString(), param);
	}
}