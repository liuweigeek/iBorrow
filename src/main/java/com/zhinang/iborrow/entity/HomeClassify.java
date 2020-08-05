package com.zhinang.iborrow.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

//首页小分类
@Entity
@Table(name = "t_home_classify")
public class HomeClassify {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //小分类名称
    @Column(length = 20)
    private String name;
    //小简介
    @Column(length = 200)
    private String description;
    //小分类封面
    private String cover;
    //小分类所包含小分类项
	/*@OneToMany(mappedBy = "homeClassify", fetch = FetchType.LAZY)
	private Set<HomeClassItem> homeClassItems = new HashSet<HomeClassItem>();*/

    //小分类包含书籍
/*	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_home_classify_product", joinColumns = {
			@javax.persistence.JoinColumn(name = "homeclassify_id") }, inverseJoinColumns = {
			@javax.persistence.JoinColumn(name = "product_id") })*/
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY,
            mappedBy = "homeClassifies")
    private List<Product> products = new ArrayList<Product>();

    //小分类所属大分类
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classzone_id")
    private HomeClassZone homeClassZone;

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

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    /*public Set<HomeClassItem> getHomeClassItems() {
        return this.homeClassItems;
    }
    public void setHomeClassItems(Set<HomeClassItem> homeClassItems) {
        this.homeClassItems = homeClassItems;
    }*/
    public HomeClassZone getHomeClassZone() {
        return this.homeClassZone;
    }

    public void setHomeClassZone(HomeClassZone homeClassZone) {
        this.homeClassZone = homeClassZone;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /*@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HomeClassify)) {
            return false;
        }
        HomeClassify e = (HomeClassify) obj;
        return e.getId().equals(id);
    }*/
}