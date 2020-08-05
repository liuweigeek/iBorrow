package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.ProductType;
import com.zhinang.iborrow.service.ProductTypeService;
import com.zhinang.iborrow.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Resource
    private BaseDao<ProductType> baseDao;

    @Override
    public void saveProductType(ProductType productType) {
        baseDao.merge(productType);
    }

    @Override
    public void deleteProductType(ProductType productType) {
        baseDao.delete(productType);
    }

    @Override
    public List<ProductType> findProductTypeList(ProductType productType, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from ProductType");
        if ((productType != null) && StringUtil.isNotEmpty(productType.getTitle())) {
            hql.append(" and title like ?");
            param.add("%" + productType.getTitle() + "%");
        }

        if (pageBean != null)
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);

        return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Long getProductTypeCount(ProductType productType) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from ProductType");
        if ((productType != null) && StringUtil.isNotEmpty(productType.getTitle())) {
            hql.append(" and title like ?");
            param.add("%" + productType.getTitle() + "%");
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public ProductType findProductTypeById(int id) {
        return baseDao.get(ProductType.class, id);
    }

    @Override
    public List<ProductType> findProductTypeByKeyWord(String keyword) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from ProductType");
        hql.append(" where title like ?");
        param.add("%" + keyword + "%");

        return baseDao.find(hql.toString(), param);
    }
}