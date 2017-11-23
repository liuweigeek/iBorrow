package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.HomeClassify;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.ProductService;
import com.zhinang.iborrow.util.StringUtil;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Resource
	private BaseDao<Product> baseDao;

	@Override
    public void saveProduct(Product product) {
		baseDao.merge(product);
	}

	@Override
    public void deleteProduct(Product product) {
		baseDao.delete(product);
	}

	@Override
    public List<Product> findProductList(Product product, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Product");
		if (product != null) {
			if (StringUtil.isNotEmpty(product.getName())) {
                hql.append(" and name like ?");
                param.add("%" + product.getName() + "%");
                hql.append(" or author like ?");
                param.add("%" + product.getName() + "%");
                hql.append(" or manufacturer like ?");
                param.add("%" + product.getName() + "%");
            }
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}
	
    @Override
    public List<Product> findProductListByHomeClassify(HomeClassify homeClassify, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select p from Product p");
        if (homeClassify != null) {
            if (homeClassify.getId() != 0) {
                hql.append(" join p.homeClassifies h where h.id = ?");
                param.add(homeClassify.getId());
            }
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

	@Override
    public Long getProductCount(Product product) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Product");
		/*if ((product != null) && StringUtil.isNotEmpty(product.getName())) {
			hql.append(" and name like ?");
			param.add("%" + product.getName() + "%");
		}*/

        if (product != null) {
            if (StringUtil.isNotEmpty(product.getName())) {
                hql.append(" and name like ?");
                param.add("%" + product.getName() + "%");
                hql.append(" or author like ?");
                param.add("%" + product.getName() + "%");
                hql.append(" or manufacturer like ?");
                param.add("%" + product.getName() + "%");
            }
        }

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
    public Long getProductCountByHomeClassify(HomeClassify homeClassify) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Product p");
        if (homeClassify != null) {
            if (homeClassify.getId() != 0) {
                hql.append(" join p.homeClassifies h where h.id = ?");
                param.add(homeClassify.getId());
            }
        }

        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

	@Override
    public Product findProductById(int id) {
		return baseDao.get(Product.class, id);
	}
	
	@Override
    public Long getProductCount(String keyword) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Product");
		if (StringUtil.isNotEmpty(keyword)) {
			hql.append(" where name like ?");
			param.add("%" + keyword + "%");
			hql.append(" or manufacturer like ?");
			param.add("%" + keyword + "%");
			hql.append(" or author like ?");
			param.add("%" + keyword + "%");
		}

		return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public List<Product> searchProductList(String keyword, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Product");
		if (StringUtil.isNotEmpty(keyword)) {
			hql.append(" where name like ?");
			param.add("%" + keyword + "%");
			hql.append(" or manufacturer like ?");
			param.add("%" + keyword + "%");
			hql.append(" or author like ?");
			param.add("%" + keyword + "%");
		}

		if (pageBean != null) {
			return baseDao.find(hql.toString(), param, pageBean);
		} else {
			return baseDao.find(hql.toString(), param);
		}
	}

    @Override
    public List<Product> searchProductListInStock(String keyword, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Product");
        if (StringUtil.isNotEmpty(keyword)) {
            hql.append(" where remainder > 0");
            hql.append(" and name like ?");
            param.add("%" + keyword + "%");
            hql.append(" or manufacturer like ?");
            param.add("%" + keyword + "%");
            hql.append(" or author like ?");
            param.add("%" + keyword + "%");
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString(), param, pageBean);
        } else {
            return baseDao.find(hql.toString(), param);
        }
    }

	@Override
	public Product findProductByNameAndNumber(String name, String number) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("from Product");
		hql.append(" where name = ?");
        hql.append(" and number = ?");
		param.add(name);
        param.add(number);
		return baseDao.get(hql.toString(), param);
	}
}