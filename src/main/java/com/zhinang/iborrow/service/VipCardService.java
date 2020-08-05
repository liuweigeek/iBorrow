package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.VipCard;

import java.util.List;

public abstract interface VipCardService {
    public abstract void saveVipCard(VipCard vipCard);

    public abstract void deleteVipCard(VipCard vipCard);

    public abstract List<VipCard> findVipCardList(VipCard vipCard, PageBean pageBean);

    public abstract Long getVipCardCount(VipCard vipCard);

    public abstract VipCard findVipCardById(int id);
}