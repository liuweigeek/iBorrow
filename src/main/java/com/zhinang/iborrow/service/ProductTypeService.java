package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.ProductType;
import com.zhinang.iborrow.entity.PageBean;
import java.util.List;

public abstract interface ProductTypeService {
	
	public abstract void saveProductType(ProductType productType);

	public abstract void deleteProductType(ProductType productType);

	public abstract List<ProductType> findProductTypeList(ProductType productType, PageBean pageBean);

	public abstract Long getProductTypeCount(ProductType productType);

	public abstract ProductType findProductTypeById(int id);
	
	public abstract List<ProductType> findProductTypeByKeyWord(String keyword);
}
