package com.zhinang.iborrow.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//产品类型
@Entity
@Table(name = "t_product_type")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//类型标签标题
	private String title;
	//分类包含产品
	@ManyToMany(mappedBy = "productTypes", fetch = FetchType.EAGER)
	private Set<Product> products = new HashSet<Product>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}