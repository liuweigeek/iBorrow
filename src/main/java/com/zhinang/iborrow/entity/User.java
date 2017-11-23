package com.zhinang.iborrow.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

//用户
@Entity
@Table(name = "t_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	//微信open_id
	private String wx_openid;
	
	//用户类型
	private Integer type;
	
	//昵称
	private String nickname;
	
	//密码
	private String password;
	
	//手机号
	@Column(name = "phone")
	private String phone;
	
	//电子邮箱
	private String email;
	
	//押金
	private Integer deposit;
	
	//积分
	private Integer integral;
	
	//已借阅数量
	@Column(name = "borrow_sum")
	private Integer borrowSum;
	
	//注册时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date regTime;

	//资料更新时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	//最后一次登录时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;
	
	//头像URL路径
	private String headimgurl;
	
	//会员号
	private String vipId;

	//会员开卡时间
	@Column(name="open_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openTime;
	
	//会员到期时间
	@Column(name="expiration")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationTime;
	
	//个人订阅车
	/*@OneToOne(cascade={javax.persistence.CascadeType.ALL})
	@JoinColumn(name="cart_id", unique=true)
	private BorrowCart borrowCart;*/
	//个人收藏
	/*@OneToOne(cascade={javax.persistence.CascadeType.ALL})
	@JoinColumn(name="favorite_id", unique=true)
	private Favorite favorite;*/
	
	//借阅车包含产品
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "t_cart_product", joinColumns = {
		@javax.persistence.JoinColumn(name = "user_id") }, inverseJoinColumns = {
		@javax.persistence.JoinColumn(name = "product_id") })
	private Set<Product> cartProducts = new HashSet<Product>();

	//个人收藏包含的产品
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "t_favorite_product", joinColumns = {
		@javax.persistence.JoinColumn(name = "user_id") }, inverseJoinColumns = {
		@javax.persistence.JoinColumn(name = "product_id") })
	private Set<Product> favoriteProducts = new HashSet<Product>();
	
	//订单
	@OneToMany(mappedBy="user", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@OrderBy("id DESC")
	private Set<Order> orders = new HashSet<Order>();
	
	//收货地址
	@OneToMany(mappedBy="user", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@OrderBy("id DESC")
	private Set<UserAddress> userAddresses = new HashSet<UserAddress>();
	
	//支付订单
	@OneToMany(mappedBy="user", cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@OrderBy("id DESC")
	private Set<Payment> payments = new HashSet<Payment>();
	
	//通知
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	@JoinColumns({@JoinColumn(name="user_id", referencedColumnName="id")})
	@OrderBy("id desc")
	private Set<Notification> notifications = new HashSet<Notification>();

	//推荐的人
	@OneToMany(mappedBy="referee", fetch=FetchType.LAZY)
	@OrderBy("id DESC")
	private Set<User> recommendeds = new HashSet<User>();

	//推荐人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referee_id")
	private User referee;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWx_openid() {
		return wx_openid;
	}

	public void setWx_openid(String wx_openid) {
		this.wx_openid = wx_openid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getBorrowSum() {
		return borrowSum;
	}

	public void setBorrowSum(Integer borrowSum) {
		this.borrowSum = borrowSum;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Set<Product> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<Product> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public Set<Product> getFavoriteProducts() {
		return favoriteProducts;
	}

	public void setFavoriteProducts(Set<Product> favoriteProducts) {
		this.favoriteProducts = favoriteProducts;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<UserAddress> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(Set<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Set<User> getRecommendeds() {
		return recommendeds;
	}

	public void setRecommendeds(Set<User> recommendeds) {
		this.recommendeds = recommendeds;
	}

	public User getReferee() {
		return referee;
	}

	public void setReferee(User referee) {
		this.referee = referee;
	}

}