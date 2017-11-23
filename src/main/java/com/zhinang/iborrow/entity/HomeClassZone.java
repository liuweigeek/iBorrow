package com.zhinang.iborrow.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//首页大分类
@Entity
@Table(name = "t_home_class_zone")
public class HomeClassZone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//大分类名称
	@Column(length = 20)
	private String name;
	//大分类简介
	@Column(length = 200)
	private String description;
	//所包含的小分类
	@OneToMany(mappedBy = "homeClassZone", fetch = FetchType.EAGER)
	private Set<HomeClassify> homeClassifies = new HashSet<HomeClassify>();

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

	public Set<HomeClassify> getHomeClassifies() {
		return this.homeClassifies;
	}

	public void setHomeClassifies(Set<HomeClassify> homeClassifies) {
		this.homeClassifies = homeClassifies;
	}
}