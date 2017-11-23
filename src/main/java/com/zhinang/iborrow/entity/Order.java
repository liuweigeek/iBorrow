package com.zhinang.iborrow.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//订单
@Entity
@Table(name = "t_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//创建时间
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
    //完成时间
    @Column(name = "finish_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;
	//所属用户
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	//收货地址
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	private UserAddress userAddress;
	//订单类型（借阅/退货）
	@Column(name = "order_type")
	private Integer orderType;
	//物流公司
	@Column(name = "express_com")
	private String expressCom;
	//物流单号
	@Column(name = "express_num")
	private String expressNum;
	//物流状态
	private Integer status;
	//是否满意
	private Boolean intact;
	//损坏情况
	@Column(name = "damage_info")
	private String damageInfo;
	//包含订单产品项
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(name = "t_order_order_item", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
			@JoinColumn(name = "order_item_id") })
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	
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

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getExpressCom() {
		return expressCom;
	}

	public void setExpressCom(String expressCom) {
		this.expressCom = expressCom;
	}

	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIntact() {
		return intact;
	}

	public void setIntact(Boolean intact) {
		this.intact = intact;
	}

	public String getDamageInfo() {
		return damageInfo;
	}

	public void setDamageInfo(String damageInfo) {
		this.damageInfo = damageInfo;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}