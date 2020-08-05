package com.zhinang.iborrow.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//用户收货地址
@Entity
@Table(name = "t_user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //收件人
    private String consignee;
    //所属省
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region1_id")
    private Region region1;
    //所属市
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region2_id")
    private Region region2;
    //所属区
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region3_id")
    private Region region3;
    //详细地址
    private String address;
    //邮政编码
    private String postcode;
    //联系电话
    private String phone;
    //所属用户
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    //所属区域全称
    private String regionName;
    //是否默认
    private Boolean firstChoice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Region getRegion1() {
        return region1;
    }

    public void setRegion1(Region region1) {
        this.region1 = region1;
    }

    public Region getRegion2() {
        return region2;
    }

    public void setRegion2(Region region2) {
        this.region2 = region2;
    }

    public Region getRegion3() {
        return region3;
    }

    public void setRegion3(Region region3) {
        this.region3 = region3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Boolean getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(Boolean firstChoice) {
        this.firstChoice = firstChoice;
    }

}