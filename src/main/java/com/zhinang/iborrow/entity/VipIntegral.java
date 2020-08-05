package com.zhinang.iborrow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//会员积分
@Entity
@Table(name = "t_vip_integral")
public class VipIntegral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //积分标题
    private String title;
    //所需积分
    private Integer integral;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIntegral() {
        return this.integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}