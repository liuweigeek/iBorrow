package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.HomeClassify;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;

import java.util.List;

public abstract interface ProductService {

    public abstract void saveProduct(Product product);

    public abstract void deleteProduct(Product product);

    public abstract List<Product> findProductList(Product product, PageBean pageBean);

    public abstract List<Product> findProductListByHomeClassify(HomeClassify homeClassify, PageBean pageBean);

    public abstract Long getProductCount(Product product);

    public abstract Long getProductCountByHomeClassify(HomeClassify homeClassify);

    public abstract Product findProductById(int id);

    public abstract List<Product> searchProductList(String keyword, PageBean pageBean);

    public abstract List<Product> searchProductListInStock(String keyword, PageBean pageBean);

    public abstract Product findProductByNameAndNumber(String name, String number);

    public abstract Long getProductCount(String keyword);
}