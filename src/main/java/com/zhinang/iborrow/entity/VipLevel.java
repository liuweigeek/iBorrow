package com.zhinang.iborrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//积分等级
@Entity
@Table(name = "t_vip_level")
public class VipLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //等级称呼
    private String title;
    //积分等级
    @Column(name = "level")
    private Integer levelNum;

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

    public Integer getLevelNum() {
        return this.levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }
}