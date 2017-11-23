package com.zhinang.iborrow.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//开通会员订单
@Entity
@Table(name = "t_vip_order")
public class VipOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//创建时间
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	//开通用户
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	//开通价格
	private Float price;
	
	private String wx_prepay_id;
	
	private String wx_pay_sign;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getWx_prepay_id() {
		return wx_prepay_id;
	}

	public void setWx_prepay_id(String wx_prepay_id) {
		this.wx_prepay_id = wx_prepay_id;
	}

	public String getWx_pay_sign() {
		return wx_pay_sign;
	}

	public void setWx_pay_sign(String wx_pay_sign) {
		this.wx_pay_sign = wx_pay_sign;
	}

}