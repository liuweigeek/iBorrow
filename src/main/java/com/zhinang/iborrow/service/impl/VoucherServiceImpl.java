package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Voucher;
import com.zhinang.iborrow.service.VoucherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("voucherService")
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private BaseDao<Voucher> baseDao;

    @Override
    public void saveVoucher(Voucher voucher) {
        baseDao.merge(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        baseDao.delete(voucher);
    }

    @Override
    public List<Voucher> findVoucherList(Voucher voucher, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Voucher");

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getVoucherCount(Voucher voucher) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Voucher");

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Voucher findVoucherById(int id) {
        return baseDao.get(Voucher.class, id);
    }

    @Override
    public Voucher findVoucherByCode(String code) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Voucher");
        hql.append(" where code = ?");
        param.add(code);

        return baseDao.get(hql.toString(), param);
    }

    @Override
    public boolean existVoucherWithCode(String code) {
        String hql = "select count(*) from Voucher where code=?";
        long count = baseDao.count(hql, new Object[]{code});

        return (count > 0);
    }
}