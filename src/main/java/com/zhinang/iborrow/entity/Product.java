package com.zhinang.iborrow.entity;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//产品
@Entity
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //编号
    private String number;
    //产品名称
    private String name;
    //封面
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_product_cover", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> covers = new LinkedList<>();
    //是否包含语音
    private Boolean audio;
    //产品类别
    private Integer type;
    //进价
    @Column(name = "purchase_price")
    private Float purchasePrice;
    //售价
    @Column(name = "sale_price")
    private Float salePrice;
    //语言
    private String language;
    //生产商，出版社
    private String manufacturer;
    //作者
    private String author;
    //装订
    private Integer bingding;
    //来源
    private String source;
    //上架位置
    private String placement;
    //简介
    @Lob
    @Column(columnDefinition = "TEXT")
    private String introduction;
    //库存
    private Integer inventory;
    //当前剩余（库存 - 已借出）
    private Integer remainder;
    //星级
    private Integer star;
    //入库记录
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @OrderBy("id DESC")
    private Set<PutHistory> putHistorys = new HashSet<PutHistory>();
    //书籍类型标签
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_product_product_type", joinColumns = {
            @javax.persistence.JoinColumn(name = "product_id")}, inverseJoinColumns = {
            @javax.persistence.JoinColumn(name = "product_type_id")})
    private Set<ProductType> productTypes = new HashSet<ProductType>();
    //评论
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @OrderBy("id DESC")
    private Set<Comment> comment = new HashSet<Comment>();

    //所属首页小分类
    //@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "t_home_classify_product", joinColumns = {
            @javax.persistence.JoinColumn(name = "product_id")}, inverseJoinColumns = {
            @javax.persistence.JoinColumn(name = "homeclassify_id")})
    private List<HomeClassify> homeClassifies = new ArrayList<>();

    //所属首页推荐区域
    //@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "t_home_zone_product", joinColumns = {
            @javax.persistence.JoinColumn(name = "product_id")}, inverseJoinColumns = {
            @javax.persistence.JoinColumn(name = "homezone_id")})
    private List<HomeZone> homeZones = new ArrayList<HomeZone>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }

    public Boolean getAudio() {
        return audio;
    }

    public void setAudio(Boolean audio) {
        this.audio = audio;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBingding() {
        return bingding;
    }

    public void setBingding(Integer bingding) {
        this.bingding = bingding;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Set<PutHistory> getPutHistorys() {
        return putHistorys;
    }

    public void setPutHistorys(Set<PutHistory> putHistorys) {
        this.putHistorys = putHistorys;
    }

    public Set<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public List<HomeClassify> getHomeClassifies() {
        return homeClassifies;
    }

    public void setHomeClassifies(List<HomeClassify> homeClassifies) {
        this.homeClassifies = homeClassifies;
    }

    public List<HomeZone> getHomeZones() {
        return homeZones;
    }

    public void setHomeZones(List<HomeZone> homeZones) {
        this.homeZones = homeZones;
    }
}