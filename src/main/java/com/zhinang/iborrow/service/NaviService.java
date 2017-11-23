package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.Navi;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface NaviService {
	public abstract void saveNavi(Navi navi);

	public abstract void deleteNavi(Navi navi);

	public abstract List<Navi> findNaviList(Navi navi, PageBean pageBean);

	public abstract Long getNaviCount(Navi navi);

	public abstract Navi findNaviById(int id);
}