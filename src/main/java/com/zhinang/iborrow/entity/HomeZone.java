package com.zhinang.iborrow.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

//首页推荐区域
@Entity
@Table(name = "t_home_zone")
public class HomeZone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //区域名称
    @Column(length = 20)
    private String name;
    //区域简介
    @Column(length = 200)
    private String description;
    //包含推荐项
	/*@OneToMany(mappedBy = "homeZone", fetch = FetchType.EAGER)
	private Set<HomeItem> homeItems = new HashSet<HomeItem>();*/

    //小分类包含书籍
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_home_zone_product", joinColumns = {
			@javax.persistence.JoinColumn(name = "homezone_id") }, inverseJoinColumns = {
			@javax.persistence.JoinColumn(name = "product_id") })*/
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER,
            mappedBy = "homeZones")
    private List<Product> products = new ArrayList<Product>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public Set<HomeItem> getHomeItems() {
        return this.homeItems;
    }
    public void setHomeItems(Set<HomeItem> homeItems) {
        this.homeItems = homeItems;
    }*/
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}