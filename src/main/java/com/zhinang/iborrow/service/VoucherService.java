package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Voucher;

import java.util.List;

public abstract interface VoucherService {
    public abstract void saveVoucher(Voucher voucher);

    public abstract void deleteVoucher(Voucher voucher);

    public abstract List<Voucher> findVoucherList(Voucher voucher, PageBean pageBean);

    public abstract Long getVoucherCount(Voucher voucher);

    public abstract Voucher findVoucherById(int id);

    public abstract Voucher findVoucherByCode(String code);

    public abstract boolean existVoucherWithCode(String code);
}