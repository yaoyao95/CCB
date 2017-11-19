
package com.CantoneseClubBBS.domain.user.mail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.User_;

/**
 * @author   AoiHoshino
 * @email    AoiHoshino999@gmail.com
 * @date     2017年4月24日
 * @version  1.0
 * @update_date 
 */
/** 用户邮箱 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name="mail",sequenceName="mail_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mail")
	private int id;

	/** 题目 */
	@Column(length = 200)
	private String title;
	/** 内容 */
	@Lob
	@Column(length = 2000)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date send_date;

	/** 标记邮件是否已读 */
	private boolean is_read;

	// =========================引用其他表===================================
	/** 发件人 */
	/* 多的一端 */
	@ManyToOne(fetch = FetchType.LAZY, // 延迟加载
			// cascade=CascadeType.REMOVE, // 级联删除
			targetEntity = User_.class) // 指定关联的持久化类
	/* 生成外键列 */
	@JoinColumn(name = "Sended_User_ID", referencedColumnName = "id")
	private User_ sended_user;

	/** 收件人 */
	/* 多的一端 */
	@ManyToOne(fetch = FetchType.LAZY, // 延迟加载
			// cascade=CascadeType.REMOVE, // 级联删除
			targetEntity = User_.class) // 指定关联的持久化类
	/* 生成外键列 */
	@JoinColumn(name = "Received_User_ID", referencedColumnName = "id")
	private User_ received_user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public User_ getSended_user() {
		return sended_user;
	}

	public void setSended_user(User_ sended_user) {
		this.sended_user = sended_user;
	}

	public User_ getReceived_user() {
		return received_user;
	}

	public void setReceived_user(User_ received_user) {
		this.received_user = received_user;
	}

	public boolean getIs_read() {
		return is_read;
	}

	public void setIs_read(boolean is_read) {
		this.is_read = is_read;
	}
}
