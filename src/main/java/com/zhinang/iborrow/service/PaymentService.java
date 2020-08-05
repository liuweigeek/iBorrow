package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Payment;

import java.util.List;

public abstract interface PaymentService {
    public abstract void savePayment(Payment payment);

    public abstract void deletePayment(Payment payment);

    public abstract List<Payment> findPaymentList(Payment payment, PageBean pageBean);

    public abstract Long getPaymentCount(Payment payment);

    public abstract Payment findPaymentById(int id);

    public abstract Payment findPaymentByoutTradeNo(String outTradeNo);
}