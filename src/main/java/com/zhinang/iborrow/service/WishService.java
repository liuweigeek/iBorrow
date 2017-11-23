package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.Wish;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface WishService {
	public abstract void saveWish(Wish wish);

	public abstract void deleteWish(Wish wish);

	public abstract List<Wish> findWishList(Wish wish, PageBean pageBean);

	public abstract Long getWishCount(Wish wish);

	public abstract Wish findWishById(int id);
}