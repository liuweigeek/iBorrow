package com.zhinang.iborrow.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//通知
@Entity
@Table(name = "t_notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//通知标题
	private String title;
	//通知内容
	private String content;
	//通知时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}