package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.HomeClassify;
import com.zhinang.iborrow.entity.PageBean;

import java.util.List;

public abstract interface HomeClassifyService {
    public abstract void saveHomeClassify(HomeClassify homeClassify);

    public abstract void deleteHomeClassify(HomeClassify homeClassify);

    public abstract List<HomeClassify> findHomeClassifyList(HomeClassify homeClassify, PageBean pageBean);

    public abstract Long getHomeClassifyCount(HomeClassify homeClassify);

    public abstract HomeClassify findHomeClassifyById(int id);

    public abstract List<HomeClassify> findHomeClassifyByKeyWord(String keyword);

}