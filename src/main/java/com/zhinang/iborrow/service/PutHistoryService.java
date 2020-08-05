package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.PutHistory;

import java.util.List;

public abstract interface PutHistoryService {
    public abstract void savePutHistory(PutHistory putHistory);

    public abstract void deletePutHistory(PutHistory putHistory);

    public abstract List<PutHistory> findPutHistoryList(PutHistory putHistory, PageBean pageBean);

    public abstract Long getPutHistoryCount(PutHistory putHistory);

    public abstract PutHistory findPutHistoryById(int id);
}