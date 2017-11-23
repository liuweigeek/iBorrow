package com.zhinang.iborrow.entity;

import javax.persistence.*;
import java.util.Date;

//用户收货地址
@Entity
@Table(name = "t_payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// 设备号
	private String device_info;
	// 随机字符串
	private String nonce_str;
	// 签名
	private String sign;
	// 商品描述
	private String body;
	// 商品详情
	private String detail;
	// 附加数据
	private String attach;
	// 微信订单号
	private String transaction_id;
	// 商户订单号
	private String out_trade_no;
	// 标价币种
	private String fee_type;
	// 标价金额
	private int total_fee;
	// 终端IP
	private String spbill_create_ip;
	// 交易起始时间
    @Temporal(TemporalType.TIMESTAMP)
	private Date time_start;
	// 交易结束时间
    @Temporal(TemporalType.TIMESTAMP)
	private Date time_expire;
	// 商品标记
	private String goods_tag;
	// 交易类型
	private String trade_type;
	// 商品ID
	private String product_id;
	// 指定支付方式
	private String limit_pay;
	// 是否完成支付
	private Boolean finish;
	// 付款银行
	private String bankType;
	// 所属用户
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

    public Date getTime_start() {
        return time_start;
    }

    public void setTime_start(Date time_start) {
        this.time_start = time_start;
    }

    public Date getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(Date time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}