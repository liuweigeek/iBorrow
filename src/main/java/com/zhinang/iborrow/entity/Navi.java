package com.zhinang.iborrow.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

//首页轮播图
@Entity
@Table(name = "t_navi")
public class Navi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//背景图
	private String background;

	//所指向文章
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "topicId")
	private Topic topic;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBackground() {
		return this.background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}