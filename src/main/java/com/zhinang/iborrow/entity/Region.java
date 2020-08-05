package com.zhinang.iborrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//地区信息
@Entity
@Table(name = "t_region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //地区编号
    @Column(name = "region_code")
    private String regionCode;
    //地区名称
    @Column(name = "region_name")
    private String regionName;
    //上级地区主键id
    @Column(name = "parent_id")
    private Integer parentId;
    //地区级别
    @Column(name = "region_level")
    private Integer regionLevel;

    @Column(name = "region_order")
    private Integer regionOrder;
    //地区英文名
    @Column(name = "region_name_en")
    private String regionNameEn;
    //地区英文简写
    @Column(name = "region_shortname_en")
    private String regionShortNameEn;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRegionLevel() {
        return this.regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Integer getRegionOrder() {
        return this.regionOrder;
    }

    public void setRegionOrder(Integer regionOrder) {
        this.regionOrder = regionOrder;
    }

    public String getRegionNameEn() {
        return this.regionNameEn;
    }

    public void setRegionNameEn(String regionNameEn) {
        this.regionNameEn = regionNameEn;
    }

    public String getRegionShortNameEn() {
        return this.regionShortNameEn;
    }

    public void setRegionShortNameEn(String regionShortNameEn) {
        this.regionShortNameEn = regionShortNameEn;
    }
}