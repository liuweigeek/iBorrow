package com.zhinang.iborrow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//会员价格信息
@Entity
@Table(name = "t_vip_card")
public class VipCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//价格
	private float price;
	//时长
	private Integer day;
	//背景图
	private String background;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}

}